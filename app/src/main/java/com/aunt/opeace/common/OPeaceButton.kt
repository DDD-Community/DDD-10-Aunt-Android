package com.aunt.opeace.common

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.aunt.opeace.ui.theme.LIGHTEN
import com.aunt.opeace.ui.theme.WHITE
import com.aunt.opeace.ui.theme.WHITE_200
import com.aunt.opeace.ui.theme.WHITE_400
import com.aunt.opeace.ui.theme.WHITE_600

@Composable
fun OPeaceButton(
    modifier: Modifier = Modifier,
    title: String = "다음",
    enabled: Boolean = true,
    containerColor: Color = LIGHTEN,
    disabledContainerColor: Color = WHITE_400,
    enabledTextColor: Color = WHITE_600,
    disabledTextColor: Color = WHITE_200,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor,
        ),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = if (enabled) enabledTextColor else disabledTextColor,
            )
        )
    }
}
