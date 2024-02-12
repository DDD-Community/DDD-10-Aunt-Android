package com.aunt.opeace.model

data class CardItem(
    val id: String = "",
    val nickname: String = "",
    val age: String = "",
    val job: String = "",
    val image: String = "",
    val title: String = "",
    val firstWord: String = "",
    val firstNumber: String = "A",
    val firstPercent: String = "",
    val firstResultList: HashMap<String, Int> = hashMapOf(),
    val secondWord: String = "",
    val secondNumber: String = "B",
    val secondPercent: String = "",
    val secondResultList: HashMap<String, Int> = hashMapOf(),
    val respondCount: Int = 0,
    val likeCount: Int = 0,
    val createdTime: Long = 0L
) {
    val firstResult: List<Pair<String, Int>>
        get() = firstResultList.map {
            it.key to it.value
        }
    val secondResult: List<Pair<String, Int>>
        get() = secondResultList.map {
            it.key to it.value
        }
}