package com.youxiang8727.firebaserealtimedatabasechat.ui.chatroom

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.Image
import coil3.compose.AsyncImage
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.Message
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.User
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.isSelf

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatroomScreen(
    modifier: Modifier = Modifier,
    chatroomId: String = "",
    user: User = User(),
) {
    val chatroomScreenViewModel: ChatroomScreenViewModel = hiltViewModel<ChatroomScreenViewModel, ChatroomScreenViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(
                chatroomId = chatroomId,
                user = user
            )
        }
    )

    val state by chatroomScreenViewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(state.toast) {
        if (state.toast.isNotEmpty()) snackbarHostState.showSnackbar(state.toast)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.topBarText
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(state.messages) { message ->
                    MessageBubble(
                        message = message,
                        user = user
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = state.inputText,
                    enabled = state.isInputTextEditable,
                    onValueChange = {
                        chatroomScreenViewModel.onInputTextChanged(it)
                    }
                )

                IconButton(
                    enabled = state.isButtonSendMessageEnable,
                    onClick = {
                        chatroomScreenViewModel.sendMessage()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(
    modifier: Modifier = Modifier,
    message: Message,
    user: User
) {
    val isSelf = remember {
        message.isSelf(user)
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = if (isSelf) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isSelf.not()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier.size(32.dp)
                            .clip(CircleShape),
                        model = user.avatar.drawable,
                        contentDescription = null,
                    )

                    Text(
                        text = user.username,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            Card {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = message.body,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}