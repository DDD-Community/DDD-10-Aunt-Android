package com.aunt.opeace.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AgePage() {
    InputPage(title = "나이 입력", subTitle = "출생 연도를 알려주세요") {
        Column {
            Spacer(modifier = Modifier.height(65.dp))
            TextField(
                value = "",
                placeholder = { AgePlaceholder() },
                onValueChange = { },
                textStyle = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.W700)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "?세대")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "정확한 연도를 입력해 주세요")
        }
    }
}

@Composable
private fun AgePlaceholder() {
    Text(text = "YYYY", style = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.W700))
}

