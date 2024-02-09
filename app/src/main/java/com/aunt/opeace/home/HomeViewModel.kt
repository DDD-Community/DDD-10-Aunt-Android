package com.aunt.opeace.home

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.model.CardItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    private val database = Firebase.firestore

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state

    init {
        getCards()
    }

    private fun getCards() {
        viewModelScope.launch {
            database.collection(COLLECTION_CARD).get()
                .addOnSuccessListener {
                    val cards = it.toObjects<CardItem>()
                    _state.value = _state.value.copy(list = cards)
                }
                .addOnFailureListener {

                }
        }
    }

    companion object {
        const val COLLECTION_CARD = "card"
    }
}

data class State(
    val list: List<CardItem> = emptyList()
)
