package com.aunt.opeace.home

import androidx.lifecycle.viewModelScope
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.constants.COLLECTION_CARD
import com.aunt.opeace.constants.firstPercentList
import com.aunt.opeace.model.CardItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
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
        //setDatabase()
    }

    fun handleEvent(event: Event) = when (event) {
        is Event.OnClickLike -> updateCard(card = event.card)
        Event.OnClickA -> {

        }

        Event.OnClickB -> {

        }
    }

    private fun getCards() {
        viewModelScope.launch {
            database.collection(COLLECTION_CARD)
                .get()
                .addOnSuccessListener {
                    val list = mutableListOf<CardItem>()
                    for (result in it) {
                        val card = result.toObject<CardItem>().copy(id = result.id)
                        list.add(card)
                    }
                    _state.value = _state.value.copy(cards = list)
                    setIsLoading(false)
                }
                .addOnFailureListener {
                    setIsLoading(false)
                }
        }
    }

    private fun updateCard(card: CardItem) {
        if (card.id.isBlank()) {
            return
        }

        viewModelScope.launch {
            runCatching {
                database.collection(COLLECTION_CARD)
                    .document(card.id)
                    .update("likeCount", card.likeCount + 1)
            }
        }
        updateLikeCount(targetCard = card)
    }

    private fun updateLikeCount(targetCard: CardItem) {
        val updatedCards = state.value.cards.map { card ->
            if (card.id == targetCard.id) {
                card.copy(likeCount = card.likeCount + 1)
            } else {
                card
            }
        }
        _state.value = _state.value.copy(cards = updatedCards)
    }

    private fun setIsLoading(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }

    private fun setDatabase() {
        viewModelScope.launch {
            val cards = getDummyCards()
            val resultCards = cards.mapIndexed { index, card ->
                val random = (0..9).random()
                val firstPercent = firstPercentList[random]
                val secondPercent = 100 - firstPercent
                card.copy(
                    firstPercent = "${firstPercentList[random]}%",
                    firstResultList = when (random) {
                        in (3..4) -> {
                            hashMapOf(
                                "Z세대" to firstPercent
                            )
                        }

                        in (5..6) -> {
                            hashMapOf(
                                "Y세대" to firstPercent / 2,
                                "M세대" to firstPercent / 2
                            )
                        }

                        in (7..8) -> {
                            hashMapOf(
                                "Y세대" to firstPercent / 3,
                                "M세대" to firstPercent / 3,
                                "베이비붐" to firstPercent / 3
                            )
                        }

                        else -> {
                            hashMapOf(
                                "Y세대" to firstPercent / 4,
                                "M세대" to firstPercent / 4,
                                "베이비붐" to firstPercent / 4,
                                "Z세대" to firstPercent / 4
                            )
                        }
                    },
                    secondPercent = "${secondPercent}%",
                    secondResultList = when (random) {
                        in (3..4) -> {
                            hashMapOf(
                                "Y세대" to secondPercent / 4,
                                "M세대" to secondPercent / 4,
                                "베이비붐" to secondPercent / 4,
                                "Z세대" to secondPercent / 4
                            )
                        }

                        in (5..6) -> {
                            hashMapOf(
                                "Y세대" to secondPercent / 3,
                                "M세대" to secondPercent / 3,
                                "Z세대" to secondPercent / 3,
                            )
                        }

                        in (7..8) -> {
                            hashMapOf(
                                "Y세대" to secondPercent / 2,
                                "M세대" to secondPercent / 2
                            )
                        }

                        else -> {
                            hashMapOf(
                                "베이비붐" to secondPercent
                            )
                        }
                    },
                    respondCount = (0..1000).random(),
                    likeCount = (0..1000).random()
                )
            }
            resultCards.forEach {
                database.collection(COLLECTION_CARD)
                    .add(it)
            }
        }
    }
}

sealed interface Event {
    data class OnClickLike(val card: CardItem) : Event
    data object OnClickA : Event
    data object OnClickB : Event
}

data class State(
    val isLoading: Boolean = true,
    val cards: List<CardItem> = emptyList()
)
