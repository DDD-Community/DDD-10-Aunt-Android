package com.aunt.opeace

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.animation.doOnEnd
import com.aunt.opeace.login.LoginActivity
import com.aunt.opeace.ui.theme.OPeaceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), BackHandlerInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSplashScreen()
    }

    override fun exit() {
        finish()
    }

    private fun initSplashScreen() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.height.toFloat()
            ).apply {
                interpolator = AnticipateInterpolator()
                duration = 2000L
                doOnEnd {
                    splashScreenView.remove()
                    showSignupScreen()
                }
            }.start()
        }
    }

    private fun showSignupScreen() {
        setContent {
            OPeaceTheme {
                //SignupScreen()
                //LoginScreen()
                //TermsScreen()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}