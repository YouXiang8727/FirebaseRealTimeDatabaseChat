package com.youxiang8727.firebaserealtimedatabasechat.ui.chatroom

import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.UiState
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.User
import com.youxiang8727.firebaserealtimedatabasechat.ui.chatroom.message.MessageUiModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ChatroomScreenUiState(
    val chatroomId: String = "",
    val inputText: String = "",
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val messages: PersistentList<MessageUiModel> = persistentListOf(),
    val toast: String = ""
): UiState {
    val topBarText = "${chatroomId.take(8)}...(${users.size})"

    val isButtonSendMessageEnable: Boolean = isLoading.not()

    val isInputTextEditable: Boolean = isLoading.not()
}
