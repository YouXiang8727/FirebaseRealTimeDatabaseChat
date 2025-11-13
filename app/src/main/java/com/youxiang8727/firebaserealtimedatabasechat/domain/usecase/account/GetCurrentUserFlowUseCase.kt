package com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.account

import com.google.firebase.auth.FirebaseUser
import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseAuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentUserFlowUseCase @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository
) {
    operator fun invoke(): Flow<FirebaseUser?> {
        return firebaseAuthenticationRepository.currentUserFlow
            .flowOn(Dispatchers.IO)
    }
}