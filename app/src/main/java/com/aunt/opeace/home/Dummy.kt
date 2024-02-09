package com.aunt.opeace.home

import com.aunt.opeace.constants.ageList
import com.aunt.opeace.constants.jobList
import com.aunt.opeace.constants.nicknameList
import com.aunt.opeace.constants.titleList
import com.aunt.opeace.constants.wordList
import com.aunt.opeace.model.CardItem

fun getDummyCards(): List<CardItem> = mutableListOf<CardItem>().apply {
    repeat(100) {
        val targetIndex = (0..9).random()
        add(
            CardItem(
                nickname = "${getRandomNickname()} $it",
                age = getRandomAge(),
                job = getRandomJob(),
                image = "",
                firstNumber = "A",
                firstWord = getWords(targetIndex).first,
                title = "${getTitle(targetIndex)} $it",
                secondNumber = "B",
                secondWord = getWords(targetIndex).second
            )
        )
    }
}

private fun getRandomJob(): String {
    return jobList.random()
}

private fun getRandomNickname(): String {
    return nicknameList.random()
}

private fun getRandomAge(): String {
    return ageList.random()
}

private fun getTitle(targetIndex: Int): String {
    return titleList[targetIndex]
}

private fun getWords(targetIndex: Int): Pair<String, String> {
    return wordList[targetIndex]
}