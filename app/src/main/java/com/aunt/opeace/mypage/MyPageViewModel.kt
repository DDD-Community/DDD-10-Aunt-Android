package com.aunt.opeace.mypage

import androidx.lifecycle.viewModelScope
import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.constants.COLLECTION_CARD
import com.aunt.opeace.model.CardItem
import com.aunt.opeace.preference.OPeacePreference
import com.aunt.opeace.model.UserInfo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val oPeacePreference: OPeacePreference
) : BaseViewModel() {
    private val database = Firebase.firestore

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        getMyCards()
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
        is Event.OnClickCard -> {
            _state.value = _state.value.copy(targetCard = event.card)
        }
        Event.OnClickDeleteCard -> deleteCard()
    }

    private fun getMyCards() {
        viewModelScope.launch {
            database.collection(COLLECTION_CARD)
                .get()
                .addOnSuccessListener {
                    val list = mutableListOf<CardItem>()
                    for (result in it) {
                        val card = result.toObject<CardItem>().copy(id = result.id)
                        list.add(card)
                    }
                    val cards = list.filter { card ->
                        card.nickname == oPeacePreference.getUserInfo().nickname
                    }
                    _state.value = _state.value.copy(cards = cards)
                }
                .addOnFailureListener {

                }
        }
    }

    private fun deleteCard() {
        val targetCard = state.value.targetCard

        viewModelScope.launch {
            database.collection(COLLECTION_CARD)
                .document(targetCard.id)
                .delete()
                .addOnSuccessListener {
                    println("????????? done")
                    updateCards(targetCard = targetCard)
                }
                .addOnFailureListener {
                    println("??????? good")
                }
        }
    }

    private fun updateCards(targetCard: CardItem) {
        val newCardList = state.value.cards.filter {
            it.id != targetCard.id
        }
        _state.value = _state.value.copy(cards = newCardList)
    }

    private fun onClickLogout() {
        oPeacePreference.setLogin(false)
        setEffect { Effect.Logout }
    }
}

data class State(
    val userInfo: UserInfo = UserInfo(),
    val cards: List<CardItem> = emptyList(),
    val targetCard: CardItem = CardItem()
)

sealed interface Event {
    data class OnClickSheetContentType(val type: SheetContentClickType) : Event
    data class OnClickCard(val card: CardItem) : Event
    data object OnClickDeleteCard : Event
}

sealed interface Effect : BaseEffect {
    data object MoveToInfo : Effect
    data object MoveToBlock : Effect
    data object Logout : Effect
    data object Quit : Effect
}