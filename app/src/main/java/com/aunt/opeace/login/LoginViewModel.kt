package com.aunt.opeace.login

import com.aunt.opeace.BaseEvent
import com.aunt.opeace.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {
    fun handleEvent(event: Event) = when (event) {
        Event.OnClickGoogleLogin -> sendEvent(Event.OnClickGoogleLogin)
        Event.OnClickKakaoLogin -> sendEvent(Event.OnClickKakaoLogin)
        Event.OnClickLoginText -> sendEvent(Event.OnClickLoginText)
    }
}

sealed interface Event : BaseEvent {
    data object OnClickGoogleLogin : Event
    data object OnClickKakaoLogin : Event
    data object OnClickLoginText : Event
}