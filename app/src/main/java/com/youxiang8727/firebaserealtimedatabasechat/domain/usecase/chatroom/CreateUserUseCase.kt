package com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom

import com.youxiang8727.firebaserealtimedatabasechat.domain.model.Avatar
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.User
import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseAuthenticationRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository
) {
    operator fun invoke(
        avatar: Avatar,
        username: String
    ): User {
        return User(
            avatar = avatar,
            uid = firebaseAuthenticationRepository.getCurrentUser()!!.uid,
            email = firebaseAuthenticationRepository.getCurrentUser()!!.email!!,
            username = username
        )
    }
}