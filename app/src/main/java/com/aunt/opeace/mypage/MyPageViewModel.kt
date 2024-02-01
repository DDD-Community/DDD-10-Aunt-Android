package com.aunt.opeace.mypage

import com.aunt.opeace.BaseEvent
import com.aunt.opeace.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor() : BaseViewModel() {
    fun handleEvent(event: Event) = when (event) {
        is Event.OnClickSheetContentType -> {
            onClickSheetContentType(type = event.type)
        }
    }

    private fun onClickSheetContentType(type: SheetContentClickType) {
        when (type) {
            SheetContentClickType.INFO -> {

            }

            SheetContentClickType.BLOCK -> {
                sendEvent(Event.OnClickSheetContentType(type))
            }

            SheetContentClickType.LOGOUT -> {

            }

            SheetContentClickType.QUIT -> {

            }
        }
    }
}

sealed interface Event : BaseEvent {
    data class OnClickSheetContentType(val type: SheetContentClickType) : Event
}