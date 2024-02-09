package com.aunt.opeace.terms

import androidx.lifecycle.viewModelScope
import com.aunt.opeace.BaseEffect
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.preference.OPeacePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    private val oeacePreference: OPeacePreference
) : BaseViewModel() {
    fun handleEvent(event: Event) = when (event) {
        Event.OnClickNext -> onClickNext()
    }

    private fun onClickNext() {
        viewModelScope.launch {
            oeacePreference.setTerms()
            setEffect { Effect.MoveToLogin }
        }
    }
}

sealed interface Event {
    data object OnClickNext : Event
}

sealed interface Effect : BaseEffect {
    data object MoveToLogin : Effect
}