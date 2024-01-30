package com.aunt.opeace.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    fun handleEvent(event: Event) = when (event) {
        Event.GoogleLogin -> onClickGoogleLogin()
        Event.KakaoLogin -> onClickKakoLogin()
        Event.LoginText -> onClickLoginText()
    }

    private fun onClickGoogleLogin() {
        // NOTE : 구글 로그인 연동
    }

    private fun onClickKakoLogin() {
        // NOTE : 카카오 로그인 연동
    }

    private fun onClickLoginText() {
        // NOTE : 메인?으로 이동
    }
}

sealed interface Event {
    object GoogleLogin : Event
    object KakaoLogin : Event
    object LoginText : Event
}