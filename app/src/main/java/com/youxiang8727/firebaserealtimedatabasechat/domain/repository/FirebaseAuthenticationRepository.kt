package com.youxiang8727.firebaserealtimedatabasechat.domain.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthenticationRepository {
    val currentUserFlow: Flow<FirebaseUser?>

    fun getCurrentUser(): FirebaseUser?

    suspend fun signInWithEmailAndPassword(email: String, password: String)

    suspend fun createUserWithEmailAndPassword(email: String, password: String)
}