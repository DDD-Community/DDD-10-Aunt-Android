package com.aunt.opeace.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar

@HiltViewModel
class SignupViewModel : ViewModel() {

    private val currentYear: Int
        get() = Calendar.getInstance()[Calendar.YEAR]

    fun isValidLength(input: String): Boolean = input.length in (2..5)

    fun isValidName(input: String): Boolean =
        Regex("[^\\s\\p{P}]").toPattern().matcher(input).find()

    fun isValidAge(input: String): Boolean {
        return input.toIntOrNull()
            ?.let { it < currentYear - 14 }
            ?: false
    }

    fun getGeneration(age: Int): String {
        return when (age) {
            in (1955..1964) -> "베이비붐"
            in (1965..1980) -> "X"
            in (1981..1996) -> "M"
            in (1997..2010) -> "Z"
            else -> "?"
        }
    }
}

data class SignupUiState(
    val nickname: String,
    val age: String,
    val job: String,
)
