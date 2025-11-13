package com.youxiang8727.firebaserealtimedatabasechat.ui.account

import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.Reducer

class SignInScreenReducer: Reducer<SignInScreenUiState, SignInScreenUiEvent>() {
    override fun reduce(
        currentState: SignInScreenUiState,
        event: SignInScreenUiEvent
    ): SignInScreenUiState {
        return when (event) {
            is SignInScreenUiEvent.OnEmailInput -> {
                currentState.copy(
                    email = event.email,
                    error = ""
                )
            }

            is SignInScreenUiEvent.OnPasswordInput -> {
                currentState.copy(
                    password = event.password,
                    error = ""
                )
            }

            is SignInScreenUiEvent.OnLoading -> {
                currentState.copy(
                    isLoading = event.isLoading,
                    error = ""
                )
            }

            is SignInScreenUiEvent.OnError -> {
                currentState.copy(
                    isLoading = false,
                    error = event.error
                )
            }
        }
    }
}