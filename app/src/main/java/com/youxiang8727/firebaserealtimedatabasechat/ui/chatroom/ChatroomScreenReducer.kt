package com.youxiang8727.firebaserealtimedatabasechat.ui.chatroom

import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.Reducer

class ChatroomScreenReducer: Reducer<ChatroomScreenUiState, ChatroomScreenUiEvent>() {
    override fun reduce(
        currentState: ChatroomScreenUiState,
        event: ChatroomScreenUiEvent
    ): ChatroomScreenUiState {
        return when (event) {
            is ChatroomScreenUiEvent.OnMessageInput -> {
                currentState.copy(
                    inputText = event.message,
                    toast = ""
                )
            }

            is ChatroomScreenUiEvent.OnNewMessage -> {
                currentState.copy(
                    messages = currentState.messages.add(event.message),
                    toast = ""
                )
            }

            is ChatroomScreenUiEvent.OnUserUpdated -> {
                currentState.copy(
                    users = event.users,
                    toast = ""
                )
            }

            is ChatroomScreenUiEvent.OnToastMessage -> {
                currentState.copy(
                    toast = event.toast
                )
            }

            is ChatroomScreenUiEvent.OnLoading -> {
                currentState.copy(
                    isLoading = event.isLoading,
                    toast = ""
                )
            }
        }
    }
}