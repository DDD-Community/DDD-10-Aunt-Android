package com.aunt.opeace.block

import com.aunt.opeace.BaseEvent
import com.aunt.opeace.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BlockViewModel @Inject constructor() : BaseViewModel() {
    fun handleEvent(event: Event) = when (event) {
        is Event.OnClickBlockUser -> onClickUser(id = event.id)
    }

    private fun onClickUser(id: String) {

    }
}

sealed interface Event : BaseEvent {
    data class OnClickBlockUser(val id: String) : Event
}