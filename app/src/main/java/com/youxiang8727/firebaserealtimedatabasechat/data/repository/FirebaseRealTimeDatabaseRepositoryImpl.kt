package com.youxiang8727.firebaserealtimedatabasechat.data.repository

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.youxiang8727.firebaserealtimedatabasechat.core.extension.runTransactionAwait
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.Message
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.User
import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseRealTimeDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseRealTimeDatabaseRepositoryImpl(
    database: FirebaseDatabase
): FirebaseRealTimeDatabaseRepository {
    private val chatroomListReference: DatabaseReference = database.getReference("chatroom")

    override suspend fun joinChatroom(chatroomId: String, user: User) {
        withContext(Dispatchers.IO) {
            if (chatroomListReference.child(chatroomId).get().await().exists().not()) {
                throw RuntimeException("Chatroom($chatroomId) not found")
            }

            val ref = chatroomListReference.child(chatroomId).child("users").child(user.uid)
            ref.setValue(user).await()
            ref.onDisconnect().removeValue()
        }
    }

    override suspend fun createChatroom(chatroomId: String, user: User) {
        withContext(Dispatchers.IO) {
            val ref = chatroomListReference.child(chatroomId)

            ref.runTransactionAwait(
                action = { currentData ->
                    val existing = currentData.value != null

                    if (existing) {
                        Log.e("createChatroom", "$chatroomId abort()")
                        Transaction.abort()
                    } else {
                        currentData.child("users").child(user.uid).value = user
                        Log.d("createChatroom", "$chatroomId success")
                        Transaction.success(currentData)
                    }
                },

                onSuccess = {
                    chatroomListReference.child(chatroomId).child("users").child(user.uid).onDisconnect().removeValue()
                }
            )
        }
    }

    override suspend fun leaveChatroom(chatroomId: String, user: User) {
        withContext(Dispatchers.IO) {
            chatroomListReference.child(chatroomId).child("users").child(user.uid).removeValue()
        }
    }

    override suspend fun sendMessage(chatroomId: String, message: Map<String, Any>) {
        withContext(Dispatchers.IO) {
            val ref = chatroomListReference.child(chatroomId).child("messages")

            ref.push().setValue(message).await()
        }
    }

    /** 監聽所有成員 **/
    override fun chatroomUsersFlow(chatroomId: String): Flow<List<User>> = callbackFlow {
        val ref = chatroomListReference.child(chatroomId).child("users")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.children.mapNotNull {
                    it.getValue(User::class.java)
                }

                trySend(users)
            }

            override fun onCancelled(error: DatabaseError) {
                close(RuntimeException(error.message))
            }
        }

        ref.addValueEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }
    }

    /** 監聽 “進入聊天室以後” 的新訊息 **/
    override fun chatroomMessagesFlow(chatroomId: String): Flow<Message> = callbackFlow {
        val ref = chatroomListReference.child(chatroomId).child("messages")
        val nowKey = ref.push().key!!

        val listener = object : ChildEventListener {
            override fun onChildAdded(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                snapshot.getValue(Message::class.java)?.let {
                    trySend(it)
                }
            }

            override fun onChildChanged(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                Log.d("chatroomMessagesFlow", "$chatroomId onChildChanged, snapshot: $snapshot, previousChildName: $previousChildName")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.e("chatroomMessagesFlow", "$chatroomId onChildRemoved, snapshot: $snapshot")
            }

            override fun onChildMoved(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                Log.d("chatroomMessagesFlow", "$chatroomId onChildMoved, snapshot: $snapshot, previousChildName: $previousChildName")
            }

            override fun onCancelled(error: DatabaseError) {
                close(RuntimeException(error.message))
            }
        }

        ref.orderByKey().startAfter(nowKey).addChildEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }
    }
}