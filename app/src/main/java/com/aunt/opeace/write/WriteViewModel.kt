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
        is Event.SetFirstAnswer -> {
            _state.value = _state.value.copy(firstAnswer = event.answer)
        }

        is Event.SetSecondAnswer -> {
            _state.value = _state.value.copy(secondAnswer = event.answer)
        }

        Event.OnClickButton -> {
            // save firestore
        }
    }

    private fun setText(text: String) {
        _state.value = _state.value.copy(text = text)
    }
}

data class State(
    val text: String = "",
    val firstAnswer: String = "",
    val secondAnswer: String = ""
)

sealed interface Event {
    data class SetText(val text: String) : Event
    data class SetFirstAnswer(val answer: String) : Event
    data class SetSecondAnswer(val answer: String) : Event
    data object OnClickButton : Event
}