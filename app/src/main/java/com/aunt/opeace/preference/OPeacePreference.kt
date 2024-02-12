package com.aunt.opeace.preference

import android.content.Context
import androidx.core.content.edit
import com.aunt.opeace.model.UserInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface OPeacePreference {
    fun isTerms(): Boolean
    fun setTerms()
    fun isLogin(): Boolean
    fun setLogin(isLogin: Boolean)
    fun setUserInfo(userInfo: UserInfo)
    fun getUserInfo(): UserInfo
    fun isSignup() : Boolean
    fun setSignup()

    fun deleteAll()
}

class OPeacePreferenceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : OPeacePreference {
    private val preference = context.getSharedPreferences(
        "opeace_preference", Context.MODE_PRIVATE
    )

    override fun isTerms(): Boolean {
        return preference.getBoolean("key_terms", false)
    }

    override fun setTerms() {
        preference.edit {
            putBoolean("key_terms", true)
        }
    }

    override fun isLogin(): Boolean {
        return preference.getBoolean("key_login", false)
    }

    override fun setLogin(isLogin: Boolean) {
        preference.edit {
            putBoolean("key_login", isLogin)
        }
    }

    override fun setUserInfo(userInfo: UserInfo) {
        preference.edit {
            runCatching {
                val json = Json.encodeToString(userInfo)
                putString("key_userinfo", json)
            }.onFailure { UserInfo() }
        }
    }

    override fun getUserInfo(): UserInfo {
        val json = preference.getString("key_userinfo", null)
        return if (json != null) {
            Json.decodeFromString<UserInfo>(json)
        } else {
            UserInfo()
        }
    }

    override fun isSignup(): Boolean {
        return preference.getBoolean("key_signup", false)
    }

    override fun setSignup() {
        preference.edit {
            putBoolean("key_signup", true)
        }
    }

    override fun deleteAll() {
        preference.edit().clear().apply()
    }
}