package com.youxiang8727.firebaserealtimedatabasechat.domain.repository

import com.google.firebase.database.DatabaseReference
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.Message
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.User
import kotlinx.coroutines.flow.Flow


interface FirebaseRealTimeDatabaseRepository {
    val chatroomListReference: DatabaseReference

    suspend fun joinChatroom(chatroomId: String, user: User)

    suspend fun createChatroom(chatroomId: String, user: User)

    suspend fun leaveChatroom(chatroomId: String, user: User)

    suspend fun sendMessage(chatroomId: String, message: Map<String, Any>)

    fun chatroomUsersFlow(chatroomId: String): Flow<List<User>>

    fun chatroomMessagesFlow(chatroomId: String): Flow<Message>
}