package com.aunt.opeace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val eventChannel = Channel<BaseEvent>(Channel.BUFFERED)
    val eventFlow = eventChannel.receiveAsFlow()

    fun sendEvent(event: BaseEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }
}

interface BaseEvent