package com.youxiang8727.firebaserealtimedatabasechat.core.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface UiState

interface UiEvent

abstract class Reducer<S: UiState, E: UiEvent> {
    abstract fun reduce(currentState: S, event: E): S
}

abstract class MviViewModel<S: UiState, E: UiEvent>(
    initialState: S,
    private val reducer: Reducer<S, E>
): ViewModel() {
    private val _event: MutableSharedFlow<E> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 64
    )

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    fun dispatch(event: E) {
        _event.tryEmit(event)
    }

    init {
        viewModelScope.launch {
            _event.collect {
                Log.d("$this", "event: $it")
                _state.value = reducer.reduce(_state.value, it)
            }
        }

        viewModelScope.launch {
            state.collect {
                Log.d("$this", "state: $it")
            }
        }
    }
}