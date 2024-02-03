package com.aunt.opeace.write

import com.aunt.opeace.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor() : BaseViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state

    fun handleEvent(event: Event) = when (event) {
        is Event.SetText -> setText(text = event.text)
        Event.OnClickButton -> {
            // server
        }
    }

    private fun setText(text: String) {
        _state.value = _state.value.copy(text = text)
    }
}

data class State(
    val text: String = ""
)

sealed interface Event {
    data class SetText(val text: String) : Event
    data object OnClickButton : Event
}