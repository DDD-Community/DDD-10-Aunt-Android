package com.aunt.opeace.login

import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {
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