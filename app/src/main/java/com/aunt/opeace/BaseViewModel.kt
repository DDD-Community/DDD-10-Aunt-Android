package com.aunt.opeace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _effect = Channel<BaseEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()


    protected fun setEffect(builder: () -> BaseEffect) {
        viewModelScope.launch {
            _effect.send(element = builder())
        }
    }
}

interface BaseEffect