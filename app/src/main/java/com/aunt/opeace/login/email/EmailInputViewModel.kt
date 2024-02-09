package com.aunt.opeace.login.email

import androidx.lifecycle.viewModelScope
import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.preference.OPeacePreference
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailInputViewModel @Inject constructor(
    private val preference: OPeacePreference
) : BaseViewModel() {
    private val _state = MutableStateFlow(State())
    val state get() = _state.asStateFlow()

    fun handleEvent(event: Event) = when (event) {
        is Event.SetEmail -> {
            _state.value = _state.value.copy(email = event.email)
        }

        is Event.SetPassword -> {
            _state.value = _state.value.copy(password = event.password)
        }

        Event.OnClickLogin -> requestLogin()
    }

    private fun requestLogin() {
        if (state.value.email.isBlank() || state.value.password.isBlank()) {
            return
        }

        val email = state.value.email
        val password = state.value.password

        setLoading(isLoading = true)
        Firebase.auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {
            // https://firebase.google.com/docs/auth/android/start
            if (it.isSuccessful) {
                preference.setLogin()
                setEffect { Effect.LoginSuccess }
            } else {
                setEffect { Effect.LoginFail }
            }
            setLoading(isLoading = false)
        }.addOnFailureListener {
            setLoading(isLoading = false)
            setEffect { Effect.LoginFail }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }
}

data class State(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)

sealed interface Event {
    data class SetEmail(val email: String) : Event
    data class SetPassword(val password: String) : Event
    data object OnClickLogin : Event
}

sealed interface Effect : BaseEffect {
    data object LoginSuccess: Effect
    data object LoginFail : Effect
}