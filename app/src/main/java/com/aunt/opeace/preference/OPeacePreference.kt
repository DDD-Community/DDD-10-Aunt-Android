package com.aunt.opeace.preference

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface OPeacePreference {
    fun isTerms(): Boolean
    fun setTerms()
    fun isLogin(): Boolean
    fun setLogin()
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

    override fun setLogin() {
        preference.edit {
            putBoolean("key_login", true)
        }
    }

    companion object {
    }
}