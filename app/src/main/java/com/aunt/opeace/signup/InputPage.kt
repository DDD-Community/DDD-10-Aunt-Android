package com.aunt.opeace.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun InputPage(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = TextStyle(fontSize = 24.sp, fontWeight = W700))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = subTitle, style = TextStyle(fontSize = 16.sp, fontWeight = W500))
        content()
    }
}
