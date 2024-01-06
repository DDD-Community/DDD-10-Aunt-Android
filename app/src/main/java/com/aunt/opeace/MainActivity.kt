package com.aunt.opeace

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import com.aunt.opeace.ui.theme.OPeaceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSplashScreen()
        setContent {
            OPeaceTheme {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(space = 6.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                    items(5) { index ->
                        when (index) {
                            0 -> Text(text = "현")
                            1 -> Text(text = "준")
                            2 -> Text(text = "님")
                            3 -> Text(text = "바")
                            4 -> Text(text = "보")
                        }
                    }
                    item {
                        Test()
                    }
                }
            }
        }
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
                doOnEnd { splashScreenView.remove() }
            }.start()
        }
    }
}

@Composable
private fun Test() {
    Column {
        Text(text = "AGP = 8.2.1")
        Text(text = "Kotlin = 1.9.0")
        Text(text = "Retrofit")
        Text(text = "OkHttp")
        Text(text = "Gson")
        Text(text = "Coil")
        Text(text = "Hilt")
    }
}