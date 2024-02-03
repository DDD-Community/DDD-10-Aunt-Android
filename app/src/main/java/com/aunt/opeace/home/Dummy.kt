package com.aunt.opeace.home

import com.aunt.opeace.model.CardItem

fun getDummyCards(): List<CardItem> = mutableListOf<CardItem>().apply {
    repeat(100) {
        add(
            CardItem(
                nickname = "엠제이: $it",
                age = when (it) {
                    in 0..20 -> "베이비붐"
                    in 21..40 -> "X세대"
                    in 41..60 -> "M세대"
                    else -> "Z세대"
                },
                job = when (it) {
                    in 0..20 -> "금융"
                    in 21..40 -> "IT"
                    in 41..60 -> "오오"
                    else -> "몰라"
                },
                image = "",
                firstNumber = "A",
                firstWord = "신입사원",
                title = "회식 자리에서\n누가 고기 굽는 게 맞아?",
                secondNumber = "B",
                secondWord = "잘 굽는 사람"
            )
        )
    }
}