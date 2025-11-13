package com.youxiang8727.firebaserealtimedatabasechat.ui.home

import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.UiState
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.Avatar

data class HomeScreenUiState(
    val avatar: Avatar = Avatar.entries.random(),
    val username: String = "",
    val chatroomId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
): UiState {
    val isUsernameEditable = isLoading.not()

    val isChatroomIdEditable = isLoading.not()

    val isAvatarSelectable = isLoading.not()

    val isJoinChatroomButtonEnabled = username.isNotBlank() && chatroomId.isNotBlank() && isLoading.not()

    val isCreateChatroomButtonEnabled = username.isNotBlank() && isLoading.not()
}
