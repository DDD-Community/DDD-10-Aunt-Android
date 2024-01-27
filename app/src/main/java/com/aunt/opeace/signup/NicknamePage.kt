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
fun NicknamePage() {
    InputPage(title = "닉네임 입력", subTitle = "2~5자까지 입력할 수 있어요") {
        Column {
            Spacer(modifier = Modifier.height(65.dp))
            TextField(
                value = "",
                onValueChange = { },
                textStyle = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.W700)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "")
        }
    }
}
