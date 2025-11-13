package com.youxiang8727.firebaserealtimedatabasechat.ui.navigation

import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.UiState

data class MainNavigationUiState(
    val isSignedIn: Boolean = false
): UiState
