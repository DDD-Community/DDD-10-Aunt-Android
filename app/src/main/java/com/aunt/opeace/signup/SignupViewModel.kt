package com.aunt.opeace.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {

    private val currentYear: Int
        get() = Calendar.getInstance()[Calendar.YEAR]

    private val _state = SignupState()
    val state: SignupState
        get() = _state

    fun handleEvent(event: Event) = when (event) {
        is Event.SetNickname -> _state.copy(nickname = event.nickname)
    }

    private fun isValidAge(input: String): Boolean {
        return input.toIntOrNull()
            ?.let { it < currentYear - 14 }
            ?: false
    }

    private fun getGeneration(age: Int): String {
        return when (age) {
            in (1955..1964) -> "베이비붐"
            in (1965..1980) -> "X"
            in (1981..1996) -> "M"
            in (1997..2010) -> "Z"
            else -> "?"
        }
    }
}

sealed interface Event {
    data class SetNickname(val nickname: String) : Event
}

data class SignupState(
    val nickname: String = "",
    val age: String = "",
    val job: String = "",

)
