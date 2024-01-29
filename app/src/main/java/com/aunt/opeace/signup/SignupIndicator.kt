package com.aunt.opeace.signup

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.unit.dp
import com.aunt.opeace.ui.theme.LIGHTEN
import com.aunt.opeace.ui.theme.WHITE_400

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
            color = LIGHTEN,
            cornerRadius = CornerRadius(160.dp.toPx(), 160.dp.toPx())
        )
    })
}

@Composable
private fun Dot() {
    Canvas(
        modifier = Modifier.size(6.dp),
        onDraw = { drawCircle(color = WHITE_400) }
    )
}
