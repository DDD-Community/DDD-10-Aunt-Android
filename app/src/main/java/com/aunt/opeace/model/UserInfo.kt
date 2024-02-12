package com.aunt.opeace.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val nickname: String = "",
    val job: String = "",
    val age: String = ""
) {
    val generation: String
        get() = when (age.toIntOrNull()) {
            in (1955..1964) -> "베이비붐"
            in (1965..1980) -> "X세대"
            in (1981..1996) -> "M세대"
            in (1997..2010) -> "Z세대"
            else -> "알수없음"
        }
}