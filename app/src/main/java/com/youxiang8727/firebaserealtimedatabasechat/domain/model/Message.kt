package com.youxiang8727.firebaserealtimedatabasechat.domain.model

import com.google.firebase.database.ServerValue

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