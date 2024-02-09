package com.aunt.opeace.mypage

import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.preference.OPeacePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val oPeacePreference: OPeacePreference
) : BaseViewModel() {
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

sealed interface Event {
    data class OnClickSheetContentType(val type: SheetContentClickType) : Event
}

sealed interface Effect : BaseEffect {
    data object MoveToInfo : Effect
    data object MoveToBlock : Effect
    data object Logout : Effect
    data object Quit : Effect
}