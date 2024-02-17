package com.aunt.opeace.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.aunt.opeace.home.HomeActivity
import com.aunt.opeace.login.LoginActivity
import com.aunt.opeace.preference.OPeacePreference
import com.aunt.opeace.signup.SignupActivity
import com.aunt.opeace.terms.TermsActivity
import com.aunt.opeace.ui.theme.OPeaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    @Inject
    lateinit var oPeacePreference: OPeacePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            OPeaceTheme {
                SplashScreen()
            }
        }
        moveToDelayed()
    }

    private fun moveToDelayed() {
        lifecycleScope.launch {
            delay(1500)
            moveTo()
        }
    }

    private fun moveTo() {
        when {
            oPeacePreference.isTerms().not() -> {
                startActivity(Intent(this, TermsActivity::class.java))
            }

            oPeacePreference.isLogin().not() -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            oPeacePreference.isSignup().not() -> {
                startActivity(Intent(this, SignupActivity::class.java))
            }

            else -> {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }
}