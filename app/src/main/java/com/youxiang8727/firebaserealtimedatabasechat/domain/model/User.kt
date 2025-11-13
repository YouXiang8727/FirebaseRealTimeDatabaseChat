package com.youxiang8727.firebaserealtimedatabasechat.domain.model

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class User(
    val avatar: Avatar = Avatar.MOUSE,
    val uid: String = "",
    val email: String = "",
    val username: String = "",
)

class UserNavType: NavType<User>(isNullableAllowed = false) {
    override fun put(
        bundle: SavedState,
        key: String,
        value: User
    ) {
        bundle.putString(key, serializeAsValue(value))
    }

    override fun get(
        bundle: SavedState,
        key: String
    ): User? {
        return bundle.getString(key)?.let {
            parseValue(it)
        }
    }

    override fun parseValue(value: String): User {
        return Json.decodeFromString(value)
    }

    override fun serializeAsValue(value: User): String {
        return Json.encodeToString(value)
    }
}
