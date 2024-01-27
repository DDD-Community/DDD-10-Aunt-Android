package com.aunt.opeace.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun OPeaceTopBar(
    title: String,
    @DrawableRes leftImageResId: Int,
    @DrawableRes rightImageResId: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = leftImageResId),
            contentDescription = "Left Image",
        )
        Text(text = title)
        Image(
            painter = painterResource(id = rightImageResId),
            contentDescription = "Right Image",
        )
    }
}
