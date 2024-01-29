package com.aunt.opeace.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.aunt.opeace.ui.theme.ALERT

@Composable
fun OPeaceErrorText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.W500,
    color: Color = ALERT
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color
    )
}