package com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom

import com.youxiang8727.firebaserealtimedatabasechat.domain.model.Message
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.User
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.toMessageWithServerTimestamp
import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseRealTimeDatabaseRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    val firebaseRealTimeDatabaseRepository: FirebaseRealTimeDatabaseRepository
) {
    suspend operator fun invoke(
        chatroomId: String,
        user: User,
        message: String
    ) {
        firebaseRealTimeDatabaseRepository.sendMessage(
            chatroomId = chatroomId,
            message = Message(
                sender = user,
                body = message
            ).toMessageWithServerTimestamp()
        )
    }
}