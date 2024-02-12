package com.aunt.opeace.block

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.R
import com.aunt.opeace.common.OPeaceTopBar
import com.aunt.opeace.model.UserInfo
import com.aunt.opeace.ui.theme.Color_303030
import com.aunt.opeace.ui.theme.Color_7D7D7D
import com.aunt.opeace.ui.theme.WHITE
import com.aunt.opeace.ui.theme.WHITE_500
import com.aunt.opeace.ui.theme.WHITE_600

@Composable
fun BlockScreen() {
    val viewModel: BlockViewModel = viewModel()

    Content(
        viewModel = viewModel
    )
}

@Composable
private fun Content(viewModel: BlockViewModel) {
    val blockUsers = viewModel.state.collectAsState().value.blockUsers

    Content(
        blockUsers = blockUsers,
        onSentEvent = viewModel::handleEvent
    )
}

@Composable
private fun Content(
    blockUsers: List<UserInfo>,
    onSentEvent: (Event) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE_600)
    ) {
        OPeaceTopBar(
            title = "차단 관리",
            onClickLeftImage = {
                (context as BlockActivity).finish()
            }
        )
        if (blockUsers.isEmpty()) {
            EmptyView(modifier = Modifier.padding(top = 40.dp))
        } else {
            Text(
                modifier = Modifier.padding(
                    top = 30.dp,
                    start = 26.dp,
                    bottom = 16.dp
                ),
                text = "차단 목록",
                color = WHITE,
                fontWeight = FontWeight.W700,
                fontSize = 18.sp
            )
            List(
                list = blockUsers,
                onClickClear = {
                    onSentEvent(Event.OnClickUnLockUser(userInfo = it))
                }
            )
        }
    }
}

@Composable
private fun List(
    list: List<UserInfo>,
    onClickClear: (UserInfo) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 26.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(list.size) {
            BlockUser(
                nickname = list[it].nickname,
                job = list[it].job,
                age = list[it].age,
                onClickClear = {
                    onClickClear(list[it])
                }
            )
        }
    }
}

@Composable
private fun BlockUser(
    nickname: String,
    job: String,
    age: String,
    onClickClear: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .height(72.dp)
            .background(Color_303030)
            .padding(start = 24.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BlockText(
            text = nickname,
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            color = WHITE
        )
        Spacer(modifier = Modifier.width(8.dp))
        BlockText(
            text = job,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            color = Color_7D7D7D
        )
        Spacer(modifier = Modifier.width(8.dp))
        Spacer(
            modifier = Modifier
                .width(1.dp)
                .height(10.dp)
                .background(Color_7D7D7D)
        )
        Spacer(modifier = Modifier.width(8.dp))
        BlockText(
            modifier = Modifier.weight(1f),
            text = age,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            color = Color_7D7D7D
        )
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, color = WHITE_500)
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                )
                .clickable(onClick = onClickClear),
            text = "차단 해제",
            color = WHITE,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun BlockText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    color: Color
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color
    )
}

@Composable
private fun EmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_logo),
            contentDescription = null
        )
        Text(
            text = "차단한 유저가 없어요",
            color = WHITE,
            fontWeight = FontWeight.W700,
            fontSize = 24.sp
        )
    }
}