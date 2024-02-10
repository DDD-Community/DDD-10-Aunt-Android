package com.aunt.opeace.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunt.opeace.ui.theme.BLACK
import com.aunt.opeace.ui.theme.LIGHTEN
import com.aunt.opeace.ui.theme.WHITE_500

@Composable
fun OPeaceCard(
    modifier: Modifier = Modifier,
    nickname: String,
    job: String,
    age: String,
    image: String,
    title: String,
    firstWord: String,
    firstNumber: String,
    secondWord: String,
    secondNumber: String,
    isMore: Boolean = false,
    onClickFirstButton: () -> Unit,
    onClickSecondButton: () -> Unit,
    onClickMore: (() -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = WHITE_500)
    ) {
        OPeaceCardHeader(
            nickname = nickname,
            age = age,
            job = job,
            isMore = isMore,
            onClickMore = onClickMore
        )
        Content(
            image = image,
            title = title,
            firstWord = firstWord,
            firstNumber = firstNumber,
            secondWord = secondWord,
            secondNumber = secondNumber,
            onClickFirstButton = onClickFirstButton,
            onClickSecondButton = onClickSecondButton
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
    firstWord: String,
    firstNumber: String,
    secondWord: String,
    secondNumber: String,
    onClickFirstButton: () -> Unit,
    onClickSecondButton: () -> Unit
) {
    OPeaceCardContent(title = title) {
        OPeaceCardRespondButton(
            number = firstNumber,
            text = firstWord
        )
        Spacer(modifier = Modifier.height(8.dp))
        OPeaceCardRespondButton(
            number = secondNumber,
            text = secondWord
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = LIGHTEN),
                onClick = onClickFirstButton
            ) {
                OPeaceCardCommonText(
                    text = firstNumber,
                    color = BLACK,
                    fontWeight = FontWeight.W700,
                    fontSize = 24.sp
                )
            }
            Button(
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = LIGHTEN),
                onClick = onClickSecondButton
            ) {
                OPeaceCardCommonText(
                    text = secondNumber,
                    color = BLACK,
                    fontWeight = FontWeight.W700,
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HeaderPreview() {
    Surface(color = WHITE_500) {
        OPeaceCardHeader(nickname = "엠제이엠제이", age = "금융", job = "Z세대", isMore = false)
    }
}

@Preview(showBackground = false)
@Composable
private fun HeaderIsMorePreview() {
    Surface(color = WHITE_500) {
        OPeaceCardHeader(nickname = "엠제이엠제이", age = "금융", job = "Z세대", isMore = true)
    }
}

@Preview(showBackground = false)
@Composable
private fun ContentPreview() {
    Surface(color = WHITE_500) {
        Content(
            image = "",
            title = "회식 자리에서\n누가 고기 굽는 게 맞아?",
            firstWord = "신입사원",
            firstNumber = "A",
            secondWord = "잘 굽는 사람",
            secondNumber = "B",
            onClickFirstButton = {
            },
            onClickSecondButton = {
            }
        )
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun OPeaceCardIsMorePreview() {
    Surface(modifier = Modifier.padding(20.dp)) {
        OPeaceCard(
            nickname = "엠제이엠제이",
            job = "금융",
            age = "Z세대",
            image = "",
            title = "회식 자리에서\n 누가 고기 굽는 게 맞아?",
            firstWord = "신입사원",
            firstNumber = "A",
            secondWord = "잘 굽는 사람",
            secondNumber = "B",
            onClickFirstButton = { },
            onClickSecondButton = { }
        )
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun OPeaceCardPreview() {
    Surface(modifier = Modifier.padding(20.dp)) {
        OPeaceCard(
            nickname = "엠제이엠제이",
            job = "금융",
            age = "Z세대",
            image = "",
            isMore = false,
            title = "회식 자리에서\n 누가 고기 굽는 게 맞아?",
            firstWord = "신입사원",
            firstNumber = "A",
            secondWord = "잘 굽는 사람",
            secondNumber = "B",
            onClickFirstButton = { },
            onClickSecondButton = { }
        )
    }
}