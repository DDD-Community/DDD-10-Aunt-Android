package com.aunt.opeace.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunt.opeace.R
import com.aunt.opeace.ui.theme.Color_BB
import com.aunt.opeace.ui.theme.Color_M
import com.aunt.opeace.ui.theme.Color_X
import com.aunt.opeace.ui.theme.LIGHTEN
import com.aunt.opeace.ui.theme.WHITE
import com.aunt.opeace.ui.theme.WHITE_200
import com.aunt.opeace.ui.theme.WHITE_300

@Composable
fun OPeaceCardHeader(
    modifier: Modifier = Modifier,
    nickname: String,
    age: String,
    job: String,
    isMore: Boolean = true,
    onClickMore: (() -> Unit)? = null
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(
                top = 32.dp,
                bottom = 26.dp,
                start = 24.dp
            )
        ) {
            OPeaceCardCommonText(
                modifier = Modifier.padding(bottom = 8.dp),
                text = nickname,
                color = WHITE,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            )
            {
                OPeaceCardCommonText(
                    text = job,
                    color = WHITE_200,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp
                )
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(10.dp)
                        .background(WHITE_300)
                )
                OPeaceCardCommonText(
                    text = age,
                    color = when (age) {
                        "Z세대" -> LIGHTEN
                        "M세대" -> Color_M
                        "X세대" -> Color_X
                        "베이비붐" -> Color_BB
                        else -> LIGHTEN
                    },
                    fontWeight = FontWeight.W700,
                    fontSize = 14.sp
                )
            }
        }

        if (isMore) {
            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 24.dp, end = 24.dp)
                    .clickable {
                        onClickMore?.invoke()
                    },
                painter = painterResource(id = R.drawable.ic_card_more),
                contentDescription = null
            )
        }
    }
}

@Composable
fun OPeaceCardCommonText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize,
        textAlign = textAlign
    )
}