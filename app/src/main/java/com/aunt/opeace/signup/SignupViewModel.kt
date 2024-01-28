package com.aunt.opeace.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(SignupState())
    val state: StateFlow<SignupState>
        get() = _state.asStateFlow()

    fun handleEvent(event: Event) = when (event) {
        is Event.SetNickname -> {
            _state.value = _state.value.copy(nickname = event.nickname)
        }
        is Event.SetAge -> {
            _state.value = _state.value.copy(age = event.age)
        }
        is Event.OnClickJob -> {
            _state.value = _state.value.copy(job = event.job)
        }
    }
}

sealed interface Event {
    data class SetNickname(val nickname: String) : Event
    data class SetAge(val age: String) : Event
    data class OnClickJob(val job: String) : Event
}

data class SignupState(
    val nickname: String = "",
    val age: String = "",
    val job: String = "",
) {
    private val minimumYear: Int = Calendar.getInstance()[Calendar.YEAR] - 14
    val isValidAge: Boolean
        get() {
            return if (age.length == 4) {
                age.toIntOrNull()?.let {
                    it < minimumYear
                } ?: false
            } else {
                true
            }
        }
    val generation: String
        get() = when (age.toIntOrNull()) {
            in (1955..1964) -> "베이비붐"
            in (1965..1980) -> "X세대"
            in (1981..1996) -> "M세대"
            in (1997..2010) -> "Z세대"
            else -> ""
        }
}
