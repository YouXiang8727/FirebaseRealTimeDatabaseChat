package com.youxiang8727.firebaserealtimedatabasechat.ui.home

import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.UiEvent
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.Avatar

sealed interface HomeScreenUiEvent: UiEvent {
    data class OnAvatarSelected(val avatar: Avatar): HomeScreenUiEvent

    data class OnUsernameInput(val username: String): HomeScreenUiEvent

    data class OnChatroomIdInput(val chatroomId: String): HomeScreenUiEvent

    data class OnLoading(val isLoading: Boolean): HomeScreenUiEvent

    data class OnError(val throwable: Throwable): HomeScreenUiEvent
}