package com.youxiang8727.firebaserealtimedatabasechat.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.youxiang8727.firebaserealtimedatabasechat.R

@Composable
fun SignInScreen(modifier: Modifier = Modifier) {
    val signInScreenViewModel: SignInScreenViewModel = hiltViewModel()

    val state by signInScreenViewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) snackbarHostState.showSnackbar(state.error)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.email,
                    label = {
                        Text(
                            text = stringResource(R.string.hint_enter_email)
                        )
                    },
                    isError = state.showEmailErrorHint,
                    supportingText = {
                        if (state.showEmailErrorHint) {
                            Text(
                                text = stringResource(R.string.error_hint_email_invalid)
                            )
                        }
                    },
                    onValueChange = {
                        signInScreenViewModel.onEmailInput(it)
                    },
                    enabled = state.isEmailEditable
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.password,
                    label = {
                        Text(
                            text = stringResource(R.string.hint_enter_password)
                        )
                    },
                    isError = state.showPasswordErrorHint,
                    supportingText = {
                        if (state.showPasswordErrorHint) {
                            Text(
                                text = stringResource(R.string.error_hint_password_invalid)
                            )
                        }
                    },
                    onValueChange = {
                        signInScreenViewModel.onPasswordInput(it)
                    },
                    enabled = state.isPasswordEditable
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        signInScreenViewModel.signInWithEmailAndPassword()
                    },
                    enabled = state.isSignInButtonEnabled
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.button_sign_in)
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        signInScreenViewModel.createUserWithEmailAndPassword()
                    },
                    enabled = state.isSignInButtonEnabled
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.button_create_account)
                    )
                }
            }
        }
    }
}