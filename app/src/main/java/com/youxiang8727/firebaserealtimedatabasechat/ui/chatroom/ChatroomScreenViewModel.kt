package com.youxiang8727.firebaserealtimedatabasechat.ui.chatroom

import androidx.lifecycle.viewModelScope
import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.MviViewModel
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.Message
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.User
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.toUiModel
import com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom.ChatroomMessagesFlowUseCase
import com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom.ChatroomUsersFlowUseCase
import com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom.LeaveChatroomUseCase
import com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.chatroom.SendMessageUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ChatroomScreenViewModel.Factory::class)
class ChatroomScreenViewModel @AssistedInject constructor(
    @Assisted private val chatroomId: String,
    @Assisted private val user: User,
    chatroomUsersFlowUseCase: ChatroomUsersFlowUseCase,
    chatroomMessagesFlowUseCase: ChatroomMessagesFlowUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val leaveChatroomUseCase: LeaveChatroomUseCase

): MviViewModel<ChatroomScreenUiState, ChatroomScreenUiEvent>(
    initialState = ChatroomScreenUiState(
        chatroomId = chatroomId
    ),
    reducer = ChatroomScreenReducer()
) {
    @AssistedFactory
    interface Factory {
        fun create(
            chatroomId: String,
            user: User
        ): ChatroomScreenViewModel
    }

    val chatroomUsersFlow = chatroomUsersFlowUseCase(chatroomId = chatroomId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val chatroomMessagesFlow: SharedFlow<Message> = chatroomMessagesFlowUseCase(chatroomId = chatroomId)
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            replay = 0
        )

    fun onInputTextChanged(text: String) {
        dispatch(ChatroomScreenUiEvent.OnMessageInput(message = text))
    }

    fun sendMessage() {
        viewModelScope.launch {
            dispatch(
                ChatroomScreenUiEvent.OnLoading(isLoading = true)
            )

            try {
                sendMessageUseCase(
                    chatroomId = chatroomId,
                    user = user,
                    message = state.value.inputText
                )
            } catch (e: Exception) {
                dispatch(
                    ChatroomScreenUiEvent.OnToastMessage(toast = e.message.toString())
                )
            }

            dispatch(
                ChatroomScreenUiEvent.OnLoading(isLoading = false)
            )

            dispatch(
                ChatroomScreenUiEvent.OnMessageInput(message = "")
            )
        }
    }

    init {
        viewModelScope.launch {
            chatroomUsersFlow.collect {
                dispatch(
                    ChatroomScreenUiEvent.OnUserUpdated(users = it)
                )
            }
        }

        viewModelScope.launch {
            chatroomMessagesFlow.collect {
                dispatch(
                    ChatroomScreenUiEvent.OnNewMessage(message = it.toUiModel(user = user))
                )
            }
        }
    }

    override fun onCleared() {
        CoroutineScope(Dispatchers.IO).launch {
            leaveChatroomUseCase(chatroomId = chatroomId, user = user)
        }
        super.onCleared()
    }
}