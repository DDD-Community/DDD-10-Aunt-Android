package com.aunt.opeace.mypage

import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor() : BaseViewModel() {
    fun handleEvent(event: Event) = when (event) {
        is Event.OnClickSheetContentType -> {
            setEffect {
                when (event.type) {
                    SheetContentClickType.INFO -> Effect.MoveToInfo
                    SheetContentClickType.BLOCK -> Effect.MoveToBlock
                    SheetContentClickType.LOGOUT -> Effect.Logout
                    SheetContentClickType.QUIT -> Effect.Quit
                }
            }
        }
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