package com.youxiang8727.firebaserealtimedatabasechat.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseAuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseAuthenticationRepositoryImpl(
    private val auth: FirebaseAuth
): FirebaseAuthenticationRepository {
    override val currentUserFlow: Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser)
        }

        this@FirebaseAuthenticationRepositoryImpl.auth.addAuthStateListener(listener)
        awaitClose {
            this@FirebaseAuthenticationRepositoryImpl.auth.removeAuthStateListener(listener)
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        Log.d("signInWithEmailAndPassword", "email: $email, password: $password")
        withContext(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String) {
        Log.d("createUserWithEmailAndPassword", "email: $email, password: $password")
        withContext(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(email, password).await()
        }
    }
}