package com.aunt.opeace.write

import androidx.lifecycle.viewModelScope
import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.constants.COLLECTION_CARD
import com.aunt.opeace.model.CardItem
import com.aunt.opeace.preference.OPeacePreference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val oPeacePreference: OPeacePreference
) : BaseViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state

    fun handleEvent(event: Event) = when (event) {
        is Event.SetText -> {
            if (event.text.length > 60) {
                setEffect { Effect.InvalidInput("60자까지 작성할 수 있어요") }
            } else {
                _state.value = _state.value.copy(text = event.text)
            }
        }
        is Event.SetFirstAnswer -> {
            _state.value = _state.value.copy(firstAnswer = event.answer)
        }

        is Event.SetSecondAnswer -> {
            _state.value = _state.value.copy(secondAnswer = event.answer)
        }

        Event.OnClickButton -> uploadCard()
    }

    private fun uploadCard() {
        setIsLoading(true)
        val firstAnswer = _state.value.firstAnswer
        val secondAnswer = _state.value.secondAnswer
        val title = _state.value.text

        viewModelScope.launch {
            runCatching {
                Firebase.firestore.collection(COLLECTION_CARD)
                    .add(
                        CardItem(
                            nickname = oPeacePreference.getUserInfo().nickname,
                            age = oPeacePreference.getUserInfo().age,
                            job = oPeacePreference.getUserInfo().job,
                            title = title,
                            firstWord = firstAnswer,
                            secondWord = secondAnswer,
                            createdTime = System.currentTimeMillis()
                        )
                    )
            }.onSuccess {
                setEffect { Effect.UploadSuccess }
                setIsLoading(false)
            }.onFailure {
                setEffect { Effect.UploadFail }
                setIsLoading(false)
            }
        }
    }

    private fun setIsLoading(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }
}

data class State(
    val text: String = "",
    val firstAnswer: String = "",
    val secondAnswer: String = "",
    val isLoading: Boolean = false
)

sealed interface Event {
    data class SetText(val text: String) : Event
    data class SetFirstAnswer(val answer: String) : Event
    data class SetSecondAnswer(val answer: String) : Event
    data object OnClickButton : Event
}

sealed interface Effect : BaseEffect {
    data object UploadSuccess : Effect
    data object UploadFail : Effect
    data class InvalidInput(val message: String) : Effect
}
