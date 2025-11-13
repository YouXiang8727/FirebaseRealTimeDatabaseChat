package com.youxiang8727.firebaserealtimedatabasechat.ui.account

import android.util.Patterns
import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.UiState
import kotlinx.serialization.StringFormat

data class SignInScreenUiState(
    val email: String = "youxiang8727@gmail.com",
    val password: String = "1qaz@WSX",
    val showPassword: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
): UiState {
    val isEmailValid: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isEmailEditable: Boolean = isLoading.not()

    val showEmailErrorHint: Boolean = (isEmailValid || email.isEmpty()).not()

    val isPasswordValid: Boolean = isValidPassword()

    val isPasswordEditable: Boolean = isLoading.not()

    val showPasswordErrorHint: Boolean = (isPasswordValid || password.isEmpty()).not()

    val isSignInButtonEnabled: Boolean = isEmailValid && isPasswordValid && isLoading.not()

    private fun isValidPassword(): Boolean {
        val regex = """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()]).{8,20}$""".toRegex()
        return regex.matches(password)
    }
}
