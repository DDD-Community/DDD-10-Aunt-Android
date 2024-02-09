package com.aunt.opeace.login.email

import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.preference.OPeacePreference
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EmailInputViewModel @Inject constructor(
    private val preference: OPeacePreference
) : BaseViewModel() {
    private val opeaceAuth = Firebase.auth

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

    private fun requestLogin(user: FirebaseUser? = Firebase.auth.currentUser) {
        if (state.value.email.isBlank() || state.value.password.isBlank()) {
            return
        }

        val email = state.value.email
        val password = state.value.password

        setLoading(isLoading = true)
        val taskResult = if (user != null) {
            opeaceAuth.signInWithEmailAndPassword(
                email,
                password
            )
        } else {
            opeaceAuth.createUserWithEmailAndPassword(
                email,
                password
            )
        }
        taskResult.addOnCompleteListener {
            // https://firebase.google.com/docs/auth/android/start
            if (it.isSuccessful) {
                preference.setLogin(true)
                setEffect {
                    Effect.LoginSuccess(
                        loginType = if (user != null) {
                            LoginType.SIGNIN
                        } else {
                            LoginType.CREATE
                        }
                    )
                }
            } else {
                setEffect { Effect.LoginFail(it.exception?.message ?: "사용 할 수 없는 이메일입니다.") }
            }
            setLoading(isLoading = false)
        }.addOnFailureListener {
            setLoading(isLoading = false)
            setEffect { Effect.LoginFail(it.message ?: "사용 할 수 없는 이메일입니다.") }
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
    data class LoginSuccess(val loginType: LoginType) : Effect
    data class LoginFail(val message: String) : Effect
}

enum class LoginType {
    SIGNIN,
    CREATE;

    val isCreate: Boolean get() = this == CREATE
}