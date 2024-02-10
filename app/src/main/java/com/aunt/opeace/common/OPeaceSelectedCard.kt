package com.aunt.opeace.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunt.opeace.R
import com.aunt.opeace.ui.theme.Color_505050
import com.aunt.opeace.ui.theme.Color_9D9D9D
import com.aunt.opeace.ui.theme.WHITE_500

@Composable
fun OPeaceSelectedCard(
    modifier: Modifier = Modifier,
    nickname: String,
    job: String,
    age: String,
    image: String,
    title: String,
    firstWord: String,
    firstNumber: String,
    firstPercent: String,
    secondWord: String,
    secondNumber: String,
    secondPercent: String,
    isMore: Boolean = false,
    firstResultList: List<Pair<String, Int>>,
    secondResultList: List<Pair<String, Int>>,
    respondCount: Int,
    likeCount: Int,
    onClickMore: (() -> Unit)? = null,
    onClickLike: (() -> Unit)? = null,
    onClickDelete: (() -> Unit)? = null
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
            title = title,
            firstWord = firstWord,
            firstNumber = firstNumber,
            firstPercent = firstPercent,
            firstResultList = firstResultList,
            secondWord = secondWord,
            secondNumber = secondNumber,
            secondPercent = secondPercent,
            secondResultList = secondResultList,
            respondCount = respondCount,
            likeCount = likeCount,
            isMore = isMore,
            onClickActionButton = {
                if (isMore) {
                    onClickLike?.invoke()
                } else {
                    onClickDelete?.invoke()
                }
            }
        )
    }
}

@Composable
private fun Content(
    title: String,
    firstWord: String,
    firstNumber: String,
    firstPercent: String,
    firstResultList: List<Pair<String, Int>>,
    secondWord: String,
    secondNumber: String,
    secondPercent: String,
    secondResultList: List<Pair<String, Int>>,
    respondCount: Int,
    likeCount: Int,
    isMore: Boolean,
    onClickActionButton: () -> Unit
) {
    OPeaceCardContent(title = title) {
        OPeaceCardRespondButton(
            number = firstNumber,
            text = firstWord,
            totalPercent = firstPercent,
            list = firstResultList
        )
        Spacer(modifier = Modifier.height(8.dp))
        OPeaceCardRespondButton(
            number = secondNumber,
            text = secondWord,
            totalPercent = secondPercent,
            list = secondResultList
        )
        Spacer(modifier = Modifier.height(18.dp))
        RespondCount(respondCount = respondCount, likeCount = likeCount)
        Spacer(modifier = Modifier.height(30.dp))
        ActionButton(
            isLogin = isMore,
            onClick = onClickActionButton
        )
    }
}

@Composable
private fun RespondCount(
    modifier: Modifier = Modifier,
    respondCount: Int,
    likeCount: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = R.drawable.ic_card_respond), contentDescription = null)
        Text(
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center,
            text = "응답 $respondCount",
            color = Color_9D9D9D,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(10.dp))
        Image(painter = painterResource(id = R.drawable.ic_card_respond), contentDescription = null)
        Text(
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center,
            text = "공감 $likeCount",
            color = Color_9D9D9D,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun ActionButton(
    isLogin: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .size(64.dp)
            .border(1.dp, Color_505050, CircleShape)
            .padding(1.dp)
            .clip(CircleShape)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .size(width = 24.dp, height = 32.dp),
            painter = painterResource(
                id = if (isLogin) {
                    R.drawable.ic_login_logo
                } else {
                    R.drawable.ic_kakao_login
                }
            ),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RespondCountPreview() {
    Surface(color = WHITE_500) {
        RespondCount(respondCount = 1000, likeCount = 256)
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionButtonPreview() {
    Surface(color = WHITE_500) {
        ActionButton {}
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionButtonIsNotLoginPreview() {
    Surface(color = WHITE_500) {
        ActionButton(isLogin = false) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentPreview() {
    Surface(color = WHITE_500) {
        Content(
            title = "회식 자리에서\n누가 고기 굽는 게 맞아?",
            firstWord = "신입사원",
            firstNumber = "A",
            firstPercent = "77%",
            firstResultList = listOf(),
            secondWord = "잘 굽는 사람",
            secondNumber = "B",
            secondPercent = "50%",
            secondResultList = listOf(),
            respondCount = 100,
            likeCount = 200,
            isMore = true,
            onClickActionButton = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OPeaceSelectedCardPreview() {
    Surface(modifier = Modifier.padding(16.dp)) {
        OPeaceSelectedCard(
            nickname = "엠제이엠제이",
            job = "금융",
            age = "Z세대",
            image = "",
            title = "회식 자리에서\n누가 고기 굽는 게 맞아?",
            firstWord = "신입사원",
            firstNumber = "A",
            firstPercent = "80%",
            secondWord = "잘 굽는 사람",
            secondNumber = "B",
            secondPercent = "20%",
            firstResultList = listOf(
                "Z세대" to 50,
                "X세대" to 20,
                "M세대" to 10,
                "베이비붐" to 10
            ),
            secondResultList = listOf(),
            respondCount = 103,
            likeCount = 37
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OPeaceSelectedCardIsNotLoginPreview() {
    Surface(modifier = Modifier.padding(16.dp)) {
        OPeaceSelectedCard(
            nickname = "엠제이엠제이",
            job = "금융",
            age = "Z세대",
            image = "",
            title = "회식 자리에서\n누가 고기 굽는 게 맞아?",
            firstWord = "신입사원",
            firstNumber = "A",
            firstPercent = "80%",
            secondWord = "잘 굽는 사람",
            secondNumber = "B",
            secondPercent = "20%",
            firstResultList = listOf(
                "Z세대" to 50,
                "X세대" to 20,
                "M세대" to 10,
                "베이비붐" to 10
            ),
            secondResultList = listOf(),
            respondCount = 103,
            likeCount = 37,
            isMore = false
        )
    }
}