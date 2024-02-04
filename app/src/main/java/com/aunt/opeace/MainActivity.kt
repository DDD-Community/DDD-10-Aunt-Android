package com.aunt.opeace

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.animation.doOnEnd
import com.aunt.opeace.home.HomeActivity
import com.aunt.opeace.login.LoginActivity
import com.aunt.opeace.preference.OPeacePreference
import com.aunt.opeace.terms.TermsActivity
import com.aunt.opeace.ui.theme.OPeaceTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var opeacePreference: OPeacePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSplashScreen()
    }

    private fun initSplashScreen() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.background = getDrawable(R.drawable.splash)
            ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                0f,
                0f
            ).apply {
                interpolator = AnticipateInterpolator()
                duration = 2000L
                doOnEnd {
                    showSignupScreen()
                }
            }.start()
        }
    }

    private fun showSignupScreen() {
        setContent {
            OPeaceTheme {
                if (opeacePreference.isTerms()) {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    startActivity(Intent(this, TermsActivity::class.java))
                }
            }
        }
        finish()
    }
}