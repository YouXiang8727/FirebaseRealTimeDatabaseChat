package com.youxiang8727.firebaserealtimedatabasechat.ui.home

import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.Reducer

class HomeScreenReducer: Reducer<HomeScreenUiState, HomeScreenUiEvent>() {
    override fun reduce(
        currentState: HomeScreenUiState,
        event: HomeScreenUiEvent
    ): HomeScreenUiState {
        return when (event) {
            is HomeScreenUiEvent.OnChatroomIdInput -> {
                currentState.copy(
                    chatroomId = event.chatroomId,
                    error = null
                )
            }

            is HomeScreenUiEvent.OnUsernameInput -> {
                currentState.copy(
                    username = event.username,
                    error = null
                )
            }

            is HomeScreenUiEvent.OnError -> {
                currentState.copy(
                    error = event.throwable.message.toString(),
                    isLoading = false
                )
            }

            is HomeScreenUiEvent.OnLoading -> {
                currentState.copy(
                    isLoading = event.isLoading,
                    error = null
                )
            }

            is HomeScreenUiEvent.OnAvatarSelected -> {
                currentState.copy(
                    avatar = event.avatar,
                    error = null,
                )
            }
        }
    }
}