package com.aunt.opeace.home

import androidx.lifecycle.viewModelScope
import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.constants.COLLECTION_BLOCK
import com.aunt.opeace.constants.COLLECTION_CARD
import com.aunt.opeace.constants.FIELD_AGE
import com.aunt.opeace.constants.FIELD_CREATED_TIME
import com.aunt.opeace.constants.FIELD_JOB
import com.aunt.opeace.constants.FIELD_LIKE_COUNT
import com.aunt.opeace.constants.firstPercentList
import com.aunt.opeace.model.CardItem
import com.aunt.opeace.model.UserInfo
import com.aunt.opeace.preference.OPeacePreference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val oPeacePreference: OPeacePreference
) : BaseViewModel() {
    private val database = Firebase.firestore

    private val _state = MutableStateFlow(State(isLogin = oPeacePreference.isLogin()))
    val state: StateFlow<State> get() = _state

    fun handleEvent(event: Event) = when (event) {
        is Event.OnClickLike -> updateCard(card = event.card)
        is Event.OnClickFilter -> setFilter(filter = event.filter)
        is Event.OnClickMore -> {
            _state.value = _state.value.copy(clickedIndex = event.targetIndex)
        }

        Event.OnClickBlock -> saveBlockCard()

        Event.OnClickA -> {

        }

        Event.OnClickB -> {

        }
    }

    fun getBlockCards() {
        setIsLoading(true)
        viewModelScope.launch {
            runCatching {
                database.collection(COLLECTION_BLOCK)
                    .document(oPeacePreference.getUserInfo().nickname)
                    .collection(oPeacePreference.getUserInfo().nickname)
                    .get()
                    .addOnSuccessListener {
                        val list = mutableListOf<UserInfo>()
                        for (result in it) {
                            val userInfo = result.toObject<UserInfo>().copy(id = result.id)
                            list.add(userInfo)
                        }
                        _state.value = _state.value.copy(blockUsers = list)
                        getCards()
                    }
                    .addOnFailureListener {
                        setIsLoading(false)
                        getCards()
                    }
            }.onFailure {
                getCards()
            }
        }
    }

    private fun getCards() {
        viewModelScope.launch {
            runCatching {
                database.collection(COLLECTION_CARD)
                    .orderBy(FIELD_CREATED_TIME, Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener {
                        val list = mutableListOf<CardItem>()
                        for (result in it) {
                            val card = result.toObject<CardItem>().copy(id = result.id)
                            list.add(card)
                        }
                        val cards = list.filter { card ->
                            state.value.blockUsers.none { blockUserInfo ->
                                card.nickname == blockUserInfo.nickname
                            }
                        }
                        _state.value = _state.value.copy(cards = cards)
                        setIsLoading(false)
                    }
                    .addOnFailureListener {
                        setIsLoading(false)
                    }
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

    private fun setFilter(filter: Filter) {
        when (filter.type) {
            FilterType.JOB -> _state.value = _state.value.copy(jobText = filter.text)

            FilterType.AGE -> {
                _state.value = _state.value.copy(ageText = filter.text)
            }

            FilterType.RECENT_AND_POPULAR -> {
                _state.value = _state.value.copy(recentAndPopularText = filter.text)
            }

            FilterType.NONE -> Unit
        }

        getFilterCards(filter = filter)
    }

    private fun getFilterCards(filter: Filter) {
        when (filter.type) {
            FilterType.JOB,
            FilterType.AGE,
            FilterType.RECENT_AND_POPULAR -> requestQuery()

            FilterType.NONE -> Unit
        }
    }

    private fun requestQuery() {
        setIsLoading(true)
        viewModelScope.launch {
            getQuery().get()
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

    private fun getQuery(): Query {
        val age = state.value.ageText
        val job = state.value.jobText
        val recentAndPopular = state.value.recentAndPopularText
        var query: Query = database.collection(COLLECTION_CARD)

        age.takeIf { it != "세대" }?.let {
            query = query.whereEqualTo(FIELD_AGE, it)
        }

        job.takeIf { it != "계열" }?.let {
            query = query.whereEqualTo(FIELD_JOB, it)
        }

        recentAndPopular.takeIf { it != "정렬" }?.let {
            if (it == "최신순") {
                query = query.orderBy(FIELD_CREATED_TIME, Query.Direction.ASCENDING)
            }
            if (it == "인기순") {
                query = query.orderBy(FIELD_LIKE_COUNT, Query.Direction.DESCENDING)
            }
        }

        return query
    }

    private fun saveBlockCard() {
        val targetIndex = state.value.clickedIndex
        if (targetIndex < 0) {
            return
        }
        val targetCard = state.value.cards.getOrNull(index = targetIndex) ?: return
        deleteCard(card = targetCard)

        viewModelScope.launch {
            database.collection(COLLECTION_BLOCK)
                .document(oPeacePreference.getUserInfo().nickname)
                .collection(oPeacePreference.getUserInfo().nickname)
                .add(
                    UserInfo(
                        nickname = targetCard.nickname,
                        job = targetCard.job,
                        age = targetCard.age
                    )
                )
                .addOnSuccessListener {
                    setEffect { Effect.BlockUserSuccess }
                }
                .addOnFailureListener {
                    setEffect { Effect.BlockUserFail(it.message ?: "차단할 수 없습니다.") }
                }
        }
    }

    private fun deleteCard(card: CardItem) {
        val newCards = _state.value.copy().cards.toMutableList().filter {
            it != card
        }
        _state.value = _state.value.copy(cards = newCards)
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
                    likeCount = (0..1000).random(),
                    createdTime = System.currentTimeMillis()
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
    data class OnClickFilter(val filter: Filter) : Event
    data object OnClickBlock : Event
    data class OnClickMore(val targetIndex: Int) : Event
}

sealed interface Effect : BaseEffect {
    data object BlockUserSuccess : Effect
    data class BlockUserFail(val message: String) : Effect
}

data class State(
    val isLoading: Boolean = true,
    val cards: List<CardItem> = emptyList(),
    val blockUsers: List<UserInfo> = emptyList(),
    val jobText: String = "계열",
    val ageText: String = "세대",
    val recentAndPopularText: String = "정렬",
    val isLogin: Boolean = false,
    val clickedIndex: Int = -1
)

enum class FilterType {
    JOB,
    AGE,
    RECENT_AND_POPULAR,
    NONE;

    val isNone get() = this == NONE
}

enum class BottomSheetType {
    FILTER,
    BLOCK,
    NONE;

    val isNone get() = this == NONE
    val isFilter get() = this == FILTER
    val isBlock get() = this == BLOCK
}

data class Filter(
    val type: FilterType = FilterType.NONE,
    val text: String = ""
)
