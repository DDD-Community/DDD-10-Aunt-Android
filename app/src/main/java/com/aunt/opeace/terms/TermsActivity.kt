package com.aunt.opeace.terms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aunt.opeace.ui.theme.OPeaceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OPeaceTheme {
                TermsScreen()
            }
        }
    }
}