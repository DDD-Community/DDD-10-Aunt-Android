package com.aunt.opeace.quit

import androidx.lifecycle.viewModelScope
import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.preference.OPeacePreference
import com.aunt.opeace.signup.SignupViewModel.Companion.COLLECTION_USER
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuitViewModel @Inject constructor(
    private val oPeacePreference: OPeacePreference
) : BaseViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State>
        get() = _state

    fun handleEvent(event: Event) = when (event) {
        Event.Back -> setEffect {
            Effect.Back
        }

        Event.OnClickDone -> goodBye()

        is Event.SetText -> {
            _state.value = _state.value.copy(text = event.text)
        }
    }

    private fun goodBye() {
        setLoading(isLoading = true)

        viewModelScope.launch {
            runCatching {
                listOf(
                    async { deleteAuthUser() },
                    async { deleteUserInfo() }
                ).awaitAll()
            }.onSuccess {
                setLoading(isLoading = false)
                oPeacePreference.deleteAll()
                setEffect { Effect.QuitSuccess }
            }.onFailure {
                setLoading(isLoading = false)
            }
        }
    }

    private fun deleteAuthUser() {
        val user = Firebase.auth.currentUser ?: return

        viewModelScope.launch {
            runCatching {
                user.delete()
            }
        }
    }

    private fun deleteUserInfo() {
        val database = Firebase.firestore
        viewModelScope.launch {
            runCatching {
                database.collection(COLLECTION_USER)
                    .document(oPeacePreference.getNickname())
                    .delete()
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }
}

data class State(val text: String = "", val isLoading: Boolean = false)

sealed interface Event {
    data object Back : Event
    data object OnClickDone : Event
    data class SetText(val text: String) : Event
}

sealed interface Effect : BaseEffect {
    data object Back : Effect
    data object QuitSuccess : Effect
}