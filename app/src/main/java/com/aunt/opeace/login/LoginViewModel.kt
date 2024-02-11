package com.aunt.opeace.login

import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.preference.OPeacePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    oPeacePreference: OPeacePreference
) : BaseViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(nickname = oPeacePreference.getUserInfo().nickname)
    }

    fun handleEvent(event: Event) = when (event) {
        is Event.OnClickType -> {
            setEffect {
                when (event.type) {
                    ClickType.GOOGLE -> Effect.GoogleLogin
                    ClickType.KAKAO -> Effect.KakaoLogin
                    ClickType.TEXT -> Effect.MoveToMain
                    ClickType.EMAIL -> Effect.MoveToEmailInput
                }
            }
        }
    }
}

data class State(val nickname: String = "")

enum class ClickType {
    GOOGLE,
    KAKAO,
    TEXT,
    EMAIL
}

sealed interface Event {
    data class OnClickType(val type: ClickType) : Event
}

sealed interface Effect : BaseEffect {
    data object GoogleLogin : Effect
    data object KakaoLogin : Effect
    data object MoveToMain : Effect
    data object MoveToEmailInput : Effect
}