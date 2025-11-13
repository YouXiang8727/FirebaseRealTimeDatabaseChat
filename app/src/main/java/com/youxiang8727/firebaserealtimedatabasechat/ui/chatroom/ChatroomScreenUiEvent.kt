package com.youxiang8727.firebaserealtimedatabasechat.ui.chatroom

import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.UiEvent
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.Message
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.User

sealed interface ChatroomScreenUiEvent: UiEvent {
    data class OnMessageInput(val message: String): ChatroomScreenUiEvent

    data class OnUserUpdated(val users: List<User>): ChatroomScreenUiEvent

    data class OnNewMessage(val message: Message): ChatroomScreenUiEvent

    data class OnLoading(val isLoading: Boolean): ChatroomScreenUiEvent

    data class OnToastMessage(val toast: String): ChatroomScreenUiEvent
}