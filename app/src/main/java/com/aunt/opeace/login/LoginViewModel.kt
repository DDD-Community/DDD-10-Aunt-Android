package com.aunt.opeace.login

import android.content.Context
import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.preference.OPeacePreference
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val preference: OPeacePreference,
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

    fun kakaoLogin(context: Context) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                preference.setLogin(false)
                setEffect { Effect.Error(error.message ?: "") }
            }
            if (token != null) {
                preference.setLogin(true)
                if (preference.isSignup()) {
                    setEffect { Effect.MoveToMain }
                } else {
                    setEffect { Effect.MoveToSignUp }
                }
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        preference.setLogin(false)
                        setEffect { Effect.Error(error.message ?: "") }
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    preference.setLogin(true)
                    if (preference.isSignup()) {
                        setEffect { Effect.MoveToMain }
                    } else {
                        setEffect { Effect.MoveToSignUp }
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
}

data class State(
    val nickname: String = "",
    val isLoading: Boolean = false,
)

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
    data object MoveToSignUp : Effect
    data object MoveToEmailInput : Effect
    data class Error(val message: String) : Effect
}
