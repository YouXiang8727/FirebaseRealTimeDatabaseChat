package com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom

import com.youxiang8727.firebaserealtimedatabasechat.domain.model.User
import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseRealTimeDatabaseRepository
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateChatroomUseCase @Inject constructor(
    val realTimeDatabaseRepository: FirebaseRealTimeDatabaseRepository
) {
    @OptIn(ExperimentalUuidApi::class)
    suspend operator fun invoke(
        chatroomId: String,
        user: User
    ) {
        realTimeDatabaseRepository.createChatroom(chatroomId = chatroomId, user = user)
    }
}