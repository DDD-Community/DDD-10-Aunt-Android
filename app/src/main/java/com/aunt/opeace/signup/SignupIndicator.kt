package com.aunt.opeace.signup

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SignupIndicator(
    currentPage: Int,
    maxPage: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        for (i in 0 until maxPage) {
            if (i == currentPage) {
                CurrentDot()
            } else {
                Dot()
            }
        }
    }
}

@Composable
private fun CurrentDot() {
    Canvas(modifier = Modifier.size(18.dp, 6.dp), onDraw = {
        drawRoundRect(
            color = Color(0xff90ff00),
            cornerRadius = CornerRadius(160.dp.toPx(), 160.dp.toPx())
        )
    })
}

@Composable
private fun Dot() {
    Canvas(
        modifier = Modifier.size(6.dp),
        onDraw = { drawCircle(color = Color.Gray) }
    )
}
