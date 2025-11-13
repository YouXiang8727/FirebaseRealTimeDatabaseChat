package com.youxiang8727.firebaserealtimedatabasechat.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.User
import com.youxiang8727.firebaserealtimedatabasechat.domain.model.UserNavType
import com.youxiang8727.firebaserealtimedatabasechat.ui.account.SignInScreen
import com.youxiang8727.firebaserealtimedatabasechat.ui.chatroom.ChatroomScreen
import com.youxiang8727.firebaserealtimedatabasechat.ui.home.HomeScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
sealed class Destination {
    @Serializable
    data object SignIn: Destination()

    @Serializable
    data object Home: Destination()

    @Serializable
    data class Chatroom(val chatroomId: String, val user: User): Destination()
}


@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val naviController = rememberNavController()

    val mainNavigationViewModel: MainNavigationViewModel = hiltViewModel()

    val state by mainNavigationViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSignedIn) {
        naviController.navigate(
            if (state.isSignedIn) {
                Destination.Home
            } else {
                Destination.SignIn
            }
        ) {
            popUpTo(0) {
                inclusive = true
            }
        }
    }

    val startDestination = remember {
        Log.d("MainNavigation", "startDestination: ${state.isSignedIn}")
        if (state.isSignedIn) Destination.Home else Destination.SignIn
    }

    NavHost(
        navController = naviController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        composable<Destination.SignIn> {
            SignInScreen(
                modifier = Modifier.fillMaxSize()
            )
        }

        composable<Destination.Home> {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                navigateToChatroomScreen = { chatroom ->
                    naviController.navigate(chatroom) {
                        launchSingleTop = true
                    }
                },
            )
        }

        composable<Destination.Chatroom>(
            typeMap = mapOf(
                typeOf<User>() to UserNavType()
            )
        ) { entry ->
            val chatroom = entry.toRoute<Destination.Chatroom>()

            ChatroomScreen(
                modifier = Modifier.fillMaxSize(),
                chatroomId = chatroom.chatroomId,
                user = chatroom.user,
            )
        }
    }
}