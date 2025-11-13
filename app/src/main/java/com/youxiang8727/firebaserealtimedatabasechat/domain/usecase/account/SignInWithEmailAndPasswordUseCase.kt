package com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.account

import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseAuthenticationRepository
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        firebaseAuthenticationRepository.signInWithEmailAndPassword(email, password)
    }
}