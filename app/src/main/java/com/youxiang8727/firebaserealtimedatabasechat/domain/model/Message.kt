package com.youxiang8727.firebaserealtimedatabasechat.domain.model

import com.google.firebase.database.ServerValue
import com.youxiang8727.firebaserealtimedatabasechat.ui.chatroom.message.MessageUiModel

data class Message(
    val sender: User = User(),
    val body: String = "",
    val timestamp: Long = 0,
)

fun Message.toMessageWithServerTimestamp() = mapOf(
    "sender" to this.sender,
    "body" to this.body,
    "timestamp" to ServerValue.TIMESTAMP
)

fun Message.isSelf(user: User): Boolean {
    return this.sender.uid == user.uid
}

fun Message.toUiModel(user: User): MessageUiModel {
    return MessageUiModel(
        senderName = this.sender.username,
        senderAvatar = this.sender.avatar,
        message = this.body,
        formattedTime = "",
        isSelfMessage = this.sender.uid == user.uid,
    )
}