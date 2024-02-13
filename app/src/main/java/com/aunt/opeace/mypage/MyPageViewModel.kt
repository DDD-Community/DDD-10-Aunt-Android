package com.aunt.opeace.mypage

import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.preference.OPeacePreference
import com.aunt.opeace.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val oPeacePreference: OPeacePreference
) : BaseViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(
            userInfo = oPeacePreference.getUserInfo()
        )
    }

    fun handleEvent(event: Event) = when (event) {
        is Event.OnClickSheetContentType -> {
            when (event.type) {
                SheetContentClickType.INFO -> setEffect { Effect.MoveToInfo }
                SheetContentClickType.BLOCK -> setEffect { Effect.MoveToBlock }
                SheetContentClickType.LOGOUT -> onClickLogout()
                SheetContentClickType.QUIT -> setEffect { Effect.Quit }
            }
        }
    }

    private fun onClickLogout() {
        oPeacePreference.setLogin(false)
        setEffect { Effect.Logout }
    }
}

data class State(val userInfo: UserInfo = UserInfo())

sealed interface Event {
    data class OnClickSheetContentType(val type: SheetContentClickType) : Event
}

sealed interface Effect : BaseEffect {
    data object MoveToInfo : Effect
    data object MoveToBlock : Effect
    data object Logout : Effect
    data object Quit : Effect
}