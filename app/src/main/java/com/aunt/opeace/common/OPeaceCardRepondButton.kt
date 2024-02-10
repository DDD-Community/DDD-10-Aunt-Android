package com.aunt.opeace.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunt.opeace.ui.theme.Color_1D1D1D
import com.aunt.opeace.ui.theme.Color_BB
import com.aunt.opeace.ui.theme.Color_M
import com.aunt.opeace.ui.theme.Color_X
import com.aunt.opeace.ui.theme.LIGHTEN
import com.aunt.opeace.ui.theme.WHITE
import com.aunt.opeace.ui.theme.WHITE_200
import com.aunt.opeace.ui.theme.WHITE_400
import com.aunt.opeace.ui.theme.WHITE_500

@Composable
fun OPeaceCardRespondButton(
    modifier: Modifier = Modifier,
    number: String,
    text: String,
    totalPercent: String? = null,
    list: List<Pair<String, Int>>? = emptyList()
) {
    if (totalPercent.isNullOrBlank()) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(100.dp))
                .fillMaxWidth()
                .background(WHITE_400)
                .padding(all = 16.dp)
        ) {
            OPeaceCardCommonText(
                modifier = Modifier.align(Alignment.CenterStart),
                text = number,
                color = WHITE_200,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp
            )
            OPeaceCardCommonText(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                color = WHITE_200,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp
            )
        }
    } else {
        RespondButton(
            number = number,
            text = text,
            totalPercent = totalPercent,
            list = list ?: emptyList()
        )
    }
}

// NOTE : 결과에서 진 버튼은 디자인 요소가 부족해 결과에서 이긴 버튼 한정으로 설계함
@Composable
private fun RespondButton(
    modifier: Modifier = Modifier,
    number: String,
    text: String,
    totalPercent: String,
    list: List<Pair<String, Int>>
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .fillMaxWidth()
            .height(48.dp)
            .background(WHITE_400)
    ) {
        DrawPercent(list = list)
        OPeaceCardCommonText(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp),
            text = number,
            color = Color_1D1D1D,
            fontWeight = FontWeight.W600,
            fontSize = 18.sp
        )
        OPeaceCardCommonText(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            color = Color_1D1D1D,
            fontWeight = FontWeight.W700,
            fontSize = 14.sp
        )
        OPeaceCardCommonText(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            text = totalPercent,
            color = WHITE,
            fontWeight = FontWeight.W700,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun DrawPercent(list: List<Pair<String, Int>>) {
    if (list.isEmpty()) {
        return
    }

    Row {
        list.take(4).forEachIndexed { _, (age, percent) ->
            Spacer(
                modifier = Modifier
                    .weight(
                        if (percent != 0) {
                            percent / 100f
                        } else {
                            1f
                        }
                    )
                    .background(
                        when (age) {
                            "Z세대" -> LIGHTEN
                            "M세대" -> Color_M
                            "X세대" -> Color_X
                            "베이비붐" -> Color_BB
                            else -> WHITE
                        }
                    )
                    .fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NormalButtonPreview() {
    Surface(color = WHITE_500) {
        OPeaceCardRespondButton(number = "A", text = "신입사원")
    }
}

@Preview(showBackground = true)
@Composable
private fun Total80_RespondButtonPreview() {
    Surface(color = WHITE_500) {
        OPeaceCardRespondButton(
            number = "A",
            text = "신입사원",
            totalPercent = "80%"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OneRespondButtonPreview() {
    Surface(color = WHITE_500) {
        OPeaceCardRespondButton(
            number = "A",
            text = "신입사원",
            totalPercent = "80%",
            list = listOf("Z세대" to 70)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TwoRespondButtonPreview() {
    Surface(color = WHITE_500) {
        OPeaceCardRespondButton(
            number = "A",
            text = "신입사원",
            totalPercent = "80%",
            list = listOf(
                "Z세대" to 50,
                "M세대" to 50
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ThreeRespondButtonPreview() {
    Surface(color = WHITE_500) {
        OPeaceCardRespondButton(
            number = "A",
            text = "신입사원",
            totalPercent = "80%",
            list = listOf(
                "Z세대" to 10,
                "M세대" to 30,
                "X세대" to 30
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FourRespondButtonPreview() {
    Surface(color = WHITE_500) {
        OPeaceCardRespondButton(
            number = "A",
            text = "신입사원",
            totalPercent = "80%",
            list = listOf(
                "Z세대" to 40,
                "M세대" to 30,
                "X세대" to 10,
                "베이비붐" to 20
            )
        )
    }
}