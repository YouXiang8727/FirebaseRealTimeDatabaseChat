package com.youxiang8727.firebaserealtimedatabasechat.ui.navigation

import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.Reducer

class MainNavigationReducer: Reducer<MainNavigationUiState, MainNavigationUiEvent>() {
    override fun reduce(
        currentState: MainNavigationUiState,
        event: MainNavigationUiEvent
    ): MainNavigationUiState {
        return when (event) {
            MainNavigationUiEvent.SignIn -> {
                currentState.copy(isSignedIn = true)
            }

            MainNavigationUiEvent.SignOut -> {
                currentState.copy(isSignedIn = false)
            }
        }
    }
}