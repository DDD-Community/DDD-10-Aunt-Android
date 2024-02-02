package com.aunt.opeace.quit

import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class QuitViewModel @Inject constructor() : BaseViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State>
        get() = _state

    fun handleEvent(event: Event) = when (event) {
        Event.Back -> setEffect {
            Effect.Back
        }

        Event.OnClickDone -> setEffect {
            Effect.QuitSuccess
        }

        is Event.SetText -> {
            _state.value = _state.value.copy(text = event.text)
        }
    }
}

data class State(val text: String = "")

sealed interface Event {
    data object Back : Event
    data object OnClickDone : Event
    data class SetText(val text: String) : Event
}

sealed interface Effect : BaseEffect {
    data object Back : Effect
    data object QuitSuccess : Effect
}