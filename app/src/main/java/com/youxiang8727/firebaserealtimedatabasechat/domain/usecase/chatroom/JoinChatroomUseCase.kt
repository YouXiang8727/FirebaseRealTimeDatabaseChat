package com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom

import com.youxiang8727.firebaserealtimedatabasechat.domain.model.User
import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseRealTimeDatabaseRepository
import javax.inject.Inject

class JoinChatroomUseCase @Inject constructor(
    private val firebaseRealTimeDatabaseRepository: FirebaseRealTimeDatabaseRepository
) {
    suspend operator fun invoke(
        chatroomId: String,
        user: User
    ) {
        firebaseRealTimeDatabaseRepository.joinChatroom(
            chatroomId = chatroomId,
            user = user
        )
    }
}