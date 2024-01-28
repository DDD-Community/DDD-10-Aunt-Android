package com.aunt.opeace.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aunt.opeace.R

@Composable
fun OPeaceTopBar(
    title: String = "",
    @DrawableRes leftImageResId: Int = R.drawable.ic_24_previous,
    @DrawableRes rightImageResId: Int = 0
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray) // NOTE : 임의의 색상 지정
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = leftImageResId),
            contentDescription = "Left Image",
        )
        if (title.isNotBlank()) {
            Text(text = title)
        }

        if (rightImageResId != 0) {
            Image(
                painter = painterResource(id = rightImageResId),
                contentDescription = "Right Image",
            )
        }
    }
}
