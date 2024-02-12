package com.aunt.opeace.signup

import androidx.lifecycle.viewModelScope
import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.constants.COLLECTION_USER
import com.aunt.opeace.model.UserInfo
import com.aunt.opeace.preference.OPeacePreference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val preference: OPeacePreference
) : BaseViewModel() {
    private val database = Firebase.firestore
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

        Event.OnClickDone -> setUserInfo()
        Event.CheckNickname -> checkNickname()
    }

    private fun checkNickname() {
        val nickname = state.value.nickname
        viewModelScope.launch {
            database.collection(COLLECTION_USER)
                .document(nickname)
                .get()
                .addOnSuccessListener {
                    val userInfo = it.toObject<UserInfo>() ?: UserInfo()
                    if (userInfo.nickname.isBlank()) {
                        setEffect { Effect.NextAgePage }
                    } else {
                        setEffect { Effect.DuplicationNickname }
                    }
                }
                .addOnFailureListener {
                    setEffect { Effect.DuplicationNickname }
                }
        }
    }

    private fun setUserInfo() {
        val age = state.value.age
        val job = state.value.job
        val nickname = state.value.nickname
        saveUserInfo(userInfo = UserInfo(age = age, job = job, nickname = nickname))
    }

    private fun saveUserInfo(userInfo: UserInfo) {
        val nickname = userInfo.nickname.ifBlank { return }
        setLoading(isLoading = true)

        viewModelScope.launch {
            runCatching {
                database.collection(COLLECTION_USER)
                    .document(nickname)
                    .set(userInfo)
            }.onSuccess {
                setLoading(isLoading = false)
                preference.run {
                    setSignup()
                    setUserInfo(userInfo)
                }
                setEffect { Effect.SignupSuccess }
            }.onFailure {
                setLoading(isLoading = false)
                setEffect { Effect.SignupFail }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }
}

sealed interface Event {
    data class SetNickname(val nickname: String) : Event
    data class SetAge(val age: String) : Event
    data class OnClickJob(val job: String) : Event
    data object OnClickDone : Event
    data object CheckNickname : Event
}

data class SignupState(
    val nickname: String = "",
    val age: String = "",
    val job: String = "",
    val isLoading: Boolean = false
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

sealed interface Effect : BaseEffect {
    data object SignupSuccess : Effect
    data object SignupFail : Effect
    data object DuplicationNickname : Effect
    data object NextAgePage : Effect
}