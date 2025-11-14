package com.youxiang8727.firebaserealtimedatabasechat.ui.chatroom.message

import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.Avatar

data class MessageUiModel(
    val senderName: String = "",
    val senderAvatar: Avatar = Avatar.MOUSE,
    val message: String = "",
    val formattedTime: String = "",
    val isSelfMessage: Boolean = true
) {
    val showSenderInfo: Boolean = isSelfMessage.not()

    @Composable
    fun messageCardColors(): CardColors = if (isSelfMessage) {
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    } else {
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    @Composable
    fun contentAlignment(): Alignment = if (isSelfMessage) {
        Alignment.CenterEnd
    } else {
        Alignment.CenterStart
    }
}
