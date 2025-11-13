package com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.account

import android.util.Log
import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseAuthenticationRepository
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) {
        firebaseAuthenticationRepository.createUserWithEmailAndPassword(
            email = email,
            password = password
        )
    }
}