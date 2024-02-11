package com.aunt.opeace.home

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.R
import com.aunt.opeace.common.FlipController
import com.aunt.opeace.common.Flippable
import com.aunt.opeace.common.OPeaceCard
import com.aunt.opeace.common.OPeaceSelectedCard
import com.aunt.opeace.login.LoginActivity
import com.aunt.opeace.model.CardItem
import com.aunt.opeace.mypage.MyPageActivity
import com.aunt.opeace.ui.theme.Color_77777
import com.aunt.opeace.ui.theme.LIGHTEN
import com.aunt.opeace.ui.theme.WHITE
import com.aunt.opeace.ui.theme.WHITE_200
import com.aunt.opeace.ui.theme.WHITE_400
import com.aunt.opeace.ui.theme.WHITE_500
import com.aunt.opeace.ui.theme.WHITE_600
import com.aunt.opeace.write.WriteActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()
    val activity = LocalContext.current as HomeActivity
    val sheetState = rememberModalBottomSheetState()

    Content(
        viewModel = viewModel,
        activity = activity,
        sheetState = sheetState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    viewModel: HomeViewModel,
    activity: HomeActivity,
    sheetState: SheetState,
) {
    val cards = viewModel.state.collectAsState().value.cards
    val isLoading = viewModel.state.collectAsState().value.isLoading

    Content(
        activity = activity,
        isLogin = true,
        isLoading = isLoading,
        cards = cards,
        sheetState = sheetState,
        onSentEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    activity: HomeActivity,
    isLogin: Boolean,
    isLoading: Boolean,
    cards: List<CardItem>,
    sheetState: SheetState,
    onSentEvent: (Event) -> Unit,
) {
    var filterVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE_600)
            .padding(horizontal = 16.dp)
    ) {
        if (filterVisible) {
            ModalBottomSheet(
                onDismissRequest = { filterVisible = false },
                sheetState = sheetState,
                content = {
                    Column {
                        Text(text = "경영")
                        Text(text = "기획")
                    }
                },
            )
        }

        Box(
            modifier = Modifier.padding(it)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                item {
                    Header(
                        onClickFilter = {
                            filterVisible = true
                        },
                        onClickProfile = {
                            if (isLogin) {
                                moveToMyPage(activity = activity)
                            } else {
                                // NOTE : 다이얼로그 노출 후 확인 되면 로그인 화면으로
                                moveToLogin(activity = activity)
                            }
                        })
                }
                items(cards.size) {
                    val flipController = remember(key1 = it) { FlipController() }
                    Flippable(
                        frontSide = {
                            OPeaceCard(
                                nickname = cards[it].nickname,
                                job = cards[it].job,
                                age = cards[it].age,
                                image = "",
                                title = cards[it].title,
                                firstWord = cards[it].firstWord,
                                firstNumber = cards[it].firstNumber,
                                secondWord = cards[it].secondWord,
                                secondNumber = cards[it].secondNumber,
                                onClickFirstButton = { flipController.flip() },
                                onClickSecondButton = { flipController.flip() }
                            )
                        },
                        backSide = {
                            OPeaceSelectedCard(
                                nickname = cards[it].nickname,
                                job = cards[it].job,
                                age = cards[it].age,
                                image = "",
                                title = cards[it].title,
                                firstWord = cards[it].firstWord,
                                firstNumber = cards[it].firstNumber,
                                firstPercent = cards[it].firstPercent,
                                firstResultList = cards[it].firstResult,
                                secondWord = cards[it].secondWord,
                                secondNumber = cards[it].secondNumber,
                                secondPercent = cards[it].secondPercent,
                                secondResultList = cards[it].secondResult,
                                respondCount = cards[it].respondCount,
                                likeCount = cards[it].likeCount,
                                onClickLike = {
                                    onSentEvent(Event.OnClickLike(cards[it]))
                                }
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

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 40.dp),
                    color = WHITE
                )
            }
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    job: String = "계열",
    age: String = "세대",
    query: String = "최신순",
    onClickFilter: (String) -> Unit,
    onClickProfile: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(start = 40.dp, end = 16.dp, top = 22.dp, bottom = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            text = job,
            isSelected = true,
            onClick = onClickFilter,
        )
        FilterChip(
            text = age,
            isSelected = true,
            onClick = onClickFilter,
        )
        FilterChip(
            text = query,
            isSelected = true,
            onClick = onClickFilter,
        )
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

@Composable
private fun FilterChip(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (isSelected) LIGHTEN else WHITE_400,
                shape = RoundedCornerShape(120.dp),
            )
            .clip(RoundedCornerShape(120.dp))
            .background(color = WHITE_500)
            .clickable { onClick.invoke("") }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text =
            if (text.length > 3) text.take(2).plus("...")
            else text,
            style = TextStyle(
                color = WHITE_200,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
            ),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_bottom_arrow),
            contentDescription = "bottom_arrow"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterChipPreview() {
    Column {
        FilterChip(text = "최신순", isSelected = false) {}
        FilterChip(text = "이커머스", isSelected = true) {}
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
        Header(onClickFilter = {}, onClickProfile = {})
    }
}

