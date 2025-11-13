package com.youxiang8727.firebaserealtimedatabasechat.ui.account

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.youxiang8727.firebaserealtimedatabasechat.R
import com.youxiang8727.firebaserealtimedatabasechat.core.mvi.MviViewModel
import com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.account.CreateUserWithEmailAndPasswordUseCase
import com.youxiang8727.firebaserealtimedatabasechat.domain.usecase.account.SignInWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase,
    @ApplicationContext private val context: Context
): MviViewModel<SignInScreenUiState, SignInScreenUiEvent>(
    initialState = SignInScreenUiState(),
    reducer = SignInScreenReducer()
) {
    fun onEmailInput(email: String) {
        dispatch(
            SignInScreenUiEvent.OnEmailInput(email)
        )
    }

    fun onPasswordInput(password: String) {
        dispatch(
            SignInScreenUiEvent.OnPasswordInput(password)
        )
    }

    fun signInWithEmailAndPassword() {
        viewModelScope.launch {
            try {
                dispatch(
                    SignInScreenUiEvent.OnLoading(isLoading = true)
                )

                signInWithEmailAndPasswordUseCase(
                    email = state.value.email,
                    password = state.value.password
                )

                dispatch(
                    SignInScreenUiEvent.OnLoading(isLoading = false)
                )
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun createUserWithEmailAndPassword() {
        viewModelScope.launch {
            try {
                dispatch(
                    SignInScreenUiEvent.OnLoading(isLoading = true)
                )

                createUserWithEmailAndPasswordUseCase(
                    email = state.value.email,
                    password = state.value.password
                )

                dispatch(
                    SignInScreenUiEvent.OnLoading(isLoading = false)
                )
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    private fun handleException(
        e: Exception
    ) {
        when (e) {
            is FirebaseAuthInvalidCredentialsException -> {
                dispatch(
                    SignInScreenUiEvent.OnError(
                        error = context.getString(R.string.exception_auth_invalid)
                    )
                )
            }

            is FirebaseAuthUserCollisionException -> {
                dispatch(
                    SignInScreenUiEvent.OnError(
                        error = context.getString(R.string.exception_auth_user_collision)
                    )
                )
            }

            else -> {
                dispatch(
                    SignInScreenUiEvent.OnError(
                        error = context.getString(R.string.exception_unexpected)
                    )
                )
            }
        }
    }
}