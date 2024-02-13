package com.aunt.opeace.block

import androidx.lifecycle.viewModelScope
import com.aunt.opeace.BaseViewModel
import com.aunt.opeace.constants.COLLECTION_BLOCK
import com.aunt.opeace.model.UserInfo
import com.aunt.opeace.preference.OPeacePreference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockViewModel @Inject constructor(
    private val oPeacePreference: OPeacePreference
) : BaseViewModel() {
    private val database = Firebase.firestore

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        getBlockUsers()
    }

    fun handleEvent(event: Event) = when (event) {
        is Event.OnClickUnLockUser -> requestUnLockUser(userInfo = event.userInfo)
    }

    private fun getBlockUsers() {
        viewModelScope.launch {
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
                }
                .addOnFailureListener {
                    _state.value = _state.value.copy(blockUsers = emptyList())
                }
        }
    }

    private fun requestUnLockUser(userInfo: UserInfo) {
        viewModelScope.launch {
            database.collection(COLLECTION_BLOCK)
                .document(oPeacePreference.getUserInfo().nickname)
                .collection(oPeacePreference.getUserInfo().nickname)
                .document(userInfo.id)
                .delete()
        }
        unLockUser(userInfo = userInfo)
    }

    private fun unLockUser(userInfo: UserInfo) {
        _state.value = state.value.copy(
            blockUsers = state.value.blockUsers.filter {
                it != userInfo
            }
        )
    }
}

data class State(
    val blockUsers: List<UserInfo> = emptyList()
)

sealed interface Event {
    data class OnClickUnLockUser(val userInfo: UserInfo) : Event
}