package com.aunt.opeace.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aunt.opeace.ui.theme.OPeaceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OPeaceTheme {
                SignupScreen()
            }
        }
    }
}