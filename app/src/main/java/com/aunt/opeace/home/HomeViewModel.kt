package com.aunt.opeace.home

import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.model.CardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state

    init {
        _state.value = _state.value.copy(list = getDummyCards().shuffled())
    }
}

data class State(
    val list: List<CardItem> = emptyList()
)
