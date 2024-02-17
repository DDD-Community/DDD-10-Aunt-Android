package com.aunt.opeace.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunt.opeace.R
import com.aunt.opeace.ui.theme.WHITE_200

@Composable
fun OPeaceTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    @DrawableRes leftImageResId: Int = R.drawable.ic_back,
    onClickLeftImage: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
    ) {
        Image(
            modifier = Modifier
                .size(48.dp)
                .padding(vertical = 14.dp)
                .clickable(onClick = onClickLeftImage),
            painter = painterResource(id = leftImageResId),
            contentDescription = null
        )
        if (title.isNotBlank()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = title,
                fontSize = 20.sp,
                color = WHITE_200,
                fontWeight = FontWeight.W600
            )
        }
    }
}

@Preview
@Composable
private fun OPeaceTopBarPreview() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        OPeaceTopBar(
            title = "오피스",
            onClickLeftImage = {

            }
        )
    }
}
