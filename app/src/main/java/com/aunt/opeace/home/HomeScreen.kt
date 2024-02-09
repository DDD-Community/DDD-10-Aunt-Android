package com.aunt.opeace.home

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.R
import com.aunt.opeace.common.Flippable
import com.aunt.opeace.common.FlipController
import com.aunt.opeace.common.OPeaceCard
import com.aunt.opeace.common.OPeaceSelectedCard
import com.aunt.opeace.login.LoginActivity
import com.aunt.opeace.model.CardItem
import com.aunt.opeace.mypage.MyPageActivity
import com.aunt.opeace.ui.theme.Color_77777
import com.aunt.opeace.ui.theme.WHITE
import com.aunt.opeace.ui.theme.WHITE_500
import com.aunt.opeace.ui.theme.WHITE_600
import com.aunt.opeace.write.WriteActivity

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()
    val activity = LocalContext.current as HomeActivity

    Content(
        viewModel = viewModel,
        activity = activity
    )
}

@Composable
private fun Content(
    viewModel: HomeViewModel,
    activity: HomeActivity,
) {
    val list = viewModel.state.collectAsState().value.list

    Content(
        activity = activity,
        isLogin = true,
        list = list
    )
}

@Composable
private fun Content(
    activity: HomeActivity,
    isLogin: Boolean,
    list: List<CardItem>,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE_600)
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            item {
                Header {
                    if (isLogin) {
                        moveToMyPage(activity = activity)
                    } else {
                        // NOTE : 다이얼로그 노출 후 확인 되면 로그인 화면으로
                        moveToLogin(activity = activity)
                    }
                }
            }
            items(list.size) {
                val flipController = remember(key1 = it) { FlipController() }
                Flippable(
                    frontSide = {
                        OPeaceCard(
                            nickname = list[it].nickname,
                            job = list[it].job,
                            age = list[it].age,
                            image = "",
                            title = list[it].title,
                            firstWord = list[it].firstWord,
                            firstNumber = list[it].firstNumber,
                            secondWord = list[it].secondWord,
                            secondNumber = list[it].secondNumber,
                            onClickFirstButton = { flipController.flip() },
                            onClickSecondButton = { flipController.flip() }
                        )
                    },
                    backSide = {
                        OPeaceSelectedCard(
                            nickname = list[it].nickname,
                            job = list[it].job,
                            age = list[it].age,
                            image = "",
                            title = list[it].title,
                            firstWord = list[it].firstWord,
                            firstNumber = list[it].firstNumber,
                            firstPercent = "70%",
                            firstResultList = emptyList(),
                            secondWord = list[it].secondWord,
                            secondNumber = list[it].secondNumber,
                            secondPercent = "30%",
                            secondResultList = emptyList(),
                            respondCount = 100,
                            likeCount = 30
                        )
                    },
                    flipController = flipController
                )
            }
        }
        Text(
            modifier = Modifier
                .clickable {
                    moveToWrite(activity = activity)
                }
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(WHITE)
                .padding(
                    horizontal = 26.dp,
                    vertical = 10.dp
                ),
            text = "글쓰기",
            fontWeight = FontWeight.W700,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    onClickProfile: () -> Unit,
) {
    // NOTE : 계열, Z세대, 최신순은 디자인 요소가 부족하여 제외하고 개발
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 22.dp,
                bottom = 32.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .clickable(onClick = onClickProfile)
                .size(40.dp)
                .border(1.dp, Color_77777, CircleShape)
                .padding(1.dp)
                .clip(CircleShape)
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_home_profile),
                contentDescription = null
            )
        }
    }
}

private fun moveToWrite(activity: HomeActivity) {
    activity.startActivity(Intent(activity, WriteActivity::class.java))
}

private fun moveToMyPage(activity: HomeActivity) {
    activity.startActivity(Intent(activity, MyPageActivity::class.java))
}

private fun moveToLogin(activity: HomeActivity) {
    activity.startActivity(Intent(activity, LoginActivity::class.java))
}

@Preview
@Composable
private fun HeaderPreview() {
    Surface(color = WHITE_500) {
        Header {

        }
    }
}

