package com.youxiang8727.firebaserealtimedatabasechat.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.youxiang8727.firebaserealtimedatabasechat.R
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.Avatar
import com.youxiang8727.firebaserealtimedatabasechat.ui.navigation.Destination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(
    name = "HomeScreen",
    showBackground = true,
    showSystemUi = true
)
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToChatroomScreen: (Destination.Chatroom) -> Unit = {},
) {
    val homeScreenViewModel: HomeScreenViewModel =
        hiltViewModel<HomeScreenViewModel, HomeScreenViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(navigateToChatroomScreen)
            }
        )

    val state by homeScreenViewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                snackbarHostState
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AvatarSelector(
                    modifier = Modifier.fillMaxWidth(),
                    avatar = state.avatar,
                    onLeftClicked = homeScreenViewModel::onAvatarSelectorLeftClicked,
                    onRightClicked = homeScreenViewModel::onAvatarSelectorRightClicked,
                    enabled = state.isAvatarSelectable
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.username,
                    label = {
                        Text(
                            text = stringResource(R.string.hint_enter_username)
                        )
                    },
                    onValueChange = {
                        homeScreenViewModel.onUsernameInput(it)
                    },
                    enabled = state.isUsernameEditable
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.chatroomId,
                    label = {
                        Text(
                            text = stringResource(R.string.hint_enter_chatroom_id_to_join)
                        )
                    },
                    onValueChange = {
                        homeScreenViewModel.onChatroomIdInput(it)
                    },
                    enabled = state.isChatroomIdEditable
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        homeScreenViewModel.joinChatroom()
                    },
                    enabled = state.isJoinChatroomButtonEnabled
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.button_join_chatroom)
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        homeScreenViewModel.createAndJoinChatroom()
                    },
                    enabled = state.isCreateChatroomButtonEnabled
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.button_create_chatroom)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(
    name = "AvatarSelector",
    showBackground = true,
    showSystemUi = true
)
private fun AvatarSelector(
    modifier: Modifier = Modifier,
    avatar: Avatar = Avatar.MOUSE,
    onLeftClicked: () -> Unit = {},
    onRightClicked: () -> Unit = {},
    enabled: Boolean = true
) {
    val avatars = Avatar.entries

    val listState = rememberLazyListState()

    LaunchedEffect(avatar) {
        Log.d("AvatarSelector", "animateScrollToItem($avatar)")
        listState.animateScrollToItem(
            index = avatars.indexOf(avatar) + 1,
            scrollOffset = -listState.layoutInfo.viewportEndOffset / 2
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false
        ) {
            items(avatars) {
                val isSelected = it == avatar

                // 大小動畫
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.2f else 0.6f,
                    animationSpec = tween(400)
                )

                // 透明度動畫
                val alpha by animateFloatAsState(
                    targetValue = if (isSelected) 1f else 0.6f,
                    animationSpec = tween(400)
                )

                AsyncImage(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .alpha(alpha)
                        .size(100.dp)
                        .clip(CircleShape),
                    model = it.drawable,
                    contentDescription = null
                )
            }
        }

        IconButton(
            modifier = Modifier
                .size(32.dp)
                .align(
                    Alignment.CenterStart
                ),
            onClick = {
                onLeftClicked()
            },
            enabled = enabled
        ) {
            Icon(
                modifier = Modifier.clip(CircleShape),
                imageVector = Icons.AutoMirrored.Default.ArrowLeft,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null
            )
        }

        IconButton(
            modifier = Modifier
                .size(32.dp)
                .align(
                    Alignment.CenterEnd
                ),
            onClick = {
                onRightClicked()
            },
            enabled = enabled
        ) {
            Icon(
                modifier = Modifier.clip(CircleShape),
                imageVector = Icons.AutoMirrored.Default.ArrowRight,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null
            )
        }

    }
}
