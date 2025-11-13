package com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom

import com.youxiang8727.firebaserealtimedatabasechat.domain.model.User
import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseRealTimeDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChatroomUsersFlowUseCase @Inject constructor(
    private val firebaseRealTimeDatabaseRepository: FirebaseRealTimeDatabaseRepository
) {
    operator fun invoke(chatroomId: String): Flow<List<User>> {
        return firebaseRealTimeDatabaseRepository.chatroomUsersFlow(chatroomId = chatroomId).flowOn(
            Dispatchers.IO
        )
    }
}