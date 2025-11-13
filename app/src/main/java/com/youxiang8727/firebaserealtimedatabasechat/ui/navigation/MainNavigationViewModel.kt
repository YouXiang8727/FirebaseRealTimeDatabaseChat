package com.youxiang8727.firebaserealtimedatabasechat.ui.navigation

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.MviViewModel
import com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.account.GetCurrentUserFlowUseCase
import com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.account.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainNavigationViewModel @Inject constructor(
    getCurrentUserUseCase: GetCurrentUserUseCase,
    getCurrentUserFlowUseCase: GetCurrentUserFlowUseCase
): MviViewModel<MainNavigationUiState, MainNavigationUiEvent>(
    initialState = MainNavigationUiState(
        isSignedIn = getCurrentUserUseCase() != null
    ),
    reducer = MainNavigationReducer()
) {
    private val currentUserFlow: StateFlow<FirebaseUser?> = getCurrentUserFlowUseCase()
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000L),
            initialValue = getCurrentUserUseCase()
        )

    init {
        viewModelScope.launch {
            currentUserFlow.collect { currentUser ->
                if (currentUser == null) {
                    dispatch(MainNavigationUiEvent.SignOut)
                } else {
                    dispatch(MainNavigationUiEvent.SignIn)
                }
            }
        }
    }
}