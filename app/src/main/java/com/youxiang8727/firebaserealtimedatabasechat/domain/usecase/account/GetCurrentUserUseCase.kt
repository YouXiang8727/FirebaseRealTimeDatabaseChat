package com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.account

import com.google.firebase.auth.FirebaseUser
import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseAuthenticationRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository
) {
    operator fun invoke(): FirebaseUser? = firebaseAuthenticationRepository.getCurrentUser()
}