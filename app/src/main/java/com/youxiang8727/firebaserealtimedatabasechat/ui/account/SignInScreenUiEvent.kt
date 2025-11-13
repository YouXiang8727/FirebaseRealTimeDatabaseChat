package com.youxiang8727.firebaserealtimedatabasechat.ui.account

import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.UiEvent

sealed interface SignInScreenUiEvent: UiEvent {
    data class OnEmailInput(val email: String): SignInScreenUiEvent

    data class OnPasswordInput(val password: String): SignInScreenUiEvent

    data class OnLoading(val isLoading: Boolean): SignInScreenUiEvent

    data class OnError(val error: String): SignInScreenUiEvent
}