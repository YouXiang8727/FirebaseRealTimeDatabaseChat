package com.youxiang8727.firebaserealtimedatabasechat.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.MviViewModel
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.Avatar
import com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom.CreateChatroomUseCase
import com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom.CreateUserUseCase
import com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom.JoinChatroomUseCase
import com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom.SendMessageUseCase
import com.youxiang8727.firebaserealtimedatabasechat.ui.navigation.Destination
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@HiltViewModel(assistedFactory = HomeScreenViewModel.Factory::class)
class HomeScreenViewModel @AssistedInject constructor(
    @Assisted private val navigateToChatroomScreen: (Destination.Chatroom) -> Unit,
    private val createUserUseCase: CreateUserUseCase,
    private val createChatroomUseCase: CreateChatroomUseCase,
    private val joinChatroomUseCase: JoinChatroomUseCase
): MviViewModel<HomeScreenUiState, HomeScreenUiEvent>(
    initialState = HomeScreenUiState(),
    reducer = HomeScreenReducer()
) {
    @AssistedFactory
    interface Factory {
        fun create(navigateToUserListScreen: (Destination.Chatroom) -> Unit): HomeScreenViewModel
    }

    fun onUsernameInput(username: String) {
        dispatch(
            HomeScreenUiEvent.OnUsernameInput(username)
        )
    }

    fun onChatroomIdInput(chatroomId: String) {
        dispatch(
            HomeScreenUiEvent.OnChatroomIdInput(chatroomId)
        )
    }

    fun onAvatarSelectorLeftClicked() {
        val index = Avatar.entries.indexOf(state.value.avatar) - 1

        if (index < 0) return

        dispatch(
            HomeScreenUiEvent.OnAvatarSelected(
                avatar = Avatar.entries[index]
            )
        )
    }

    fun onAvatarSelectorRightClicked() {
        val index = Avatar.entries.indexOf(state.value.avatar) + 1

        if (index >= Avatar.entries.size) return

        dispatch(
            HomeScreenUiEvent.OnAvatarSelected(
                avatar = Avatar.entries[index]
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    fun createAndJoinChatroom() {
        viewModelScope.launch {
            dispatch(
                HomeScreenUiEvent.OnLoading(isLoading = true)
            )

            try {
                val user = createUserUseCase(
                    avatar = state.value.avatar,
                    username = state.value.username
                )

                val chatroomId = Uuid.random().toString()

                createChatroomUseCase(
                    chatroomId = chatroomId,
                    user = user
                )

                dispatch(
                    HomeScreenUiEvent.OnLoading(isLoading = false)
                )

                navigateToChatroomScreen(
                    Destination.Chatroom(
                        chatroomId = chatroomId,
                        user = user
                    )
                )
                Log.d("$this@HomeScreenViewModel", "createAndJoinChatroom() success!!")
            } catch (e: Exception) {
                e.printStackTrace()

                dispatch(
                    HomeScreenUiEvent.OnError(throwable = e)
                )
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun joinChatroom() {
        viewModelScope.launch {
            dispatch(
                HomeScreenUiEvent.OnLoading(isLoading = true)
            )

            try {
                val user = createUserUseCase(
                    avatar = state.value.avatar,
                    username = state.value.username
                )

                joinChatroomUseCase(
                    chatroomId = state.value.chatroomId,
                    user = user
                )

                dispatch(
                    HomeScreenUiEvent.OnLoading(isLoading = false)
                )

                navigateToChatroomScreen(
                    Destination.Chatroom(
                        chatroomId = state.value.chatroomId,
                        user = user
                    )
                )
                Log.d("$this@HomeScreenViewModel", "createAndJoinChatroom() success!!")
            } catch (e: Exception) {
                e.printStackTrace()

                dispatch(
                    HomeScreenUiEvent.OnError(throwable = e)
                )
            }
        }
    }
}