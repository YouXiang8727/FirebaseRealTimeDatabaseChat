package com.youxiang8727.firebaserealtimedatabasechat.ui.navigation

import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.UiEvent

sealed interface MainNavigationUiEvent: UiEvent {
    data object SignIn: MainNavigationUiEvent
    data object SignOut: MainNavigationUiEvent
}