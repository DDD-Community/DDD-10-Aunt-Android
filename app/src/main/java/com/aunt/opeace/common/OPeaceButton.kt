package com.aunt.opeace.common

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OPeaceButton(
    modifier: Modifier = Modifier,
    title: String,
    enable: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.height(54.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color(0xff90ff00),
            disabledContainerColor = Color.Gray,
        ),
        onClick = onClick,
        enabled = enable
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = if (enable) Color.Black else Color.White,
            )
        )
    }
}
