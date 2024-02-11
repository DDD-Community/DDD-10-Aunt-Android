package com.aunt.opeace.mypage

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.R
import com.aunt.opeace.block.BlockActivity
import com.aunt.opeace.common.OPeaceTopBar
import com.aunt.opeace.login.LoginActivity
import com.aunt.opeace.quit.QuitActivity
import com.aunt.opeace.signup.UserInfo
import com.aunt.opeace.ui.theme.Color_1D1D1D
import com.aunt.opeace.ui.theme.LIGHTEN
import com.aunt.opeace.ui.theme.WHITE
import com.aunt.opeace.ui.theme.WHITE_500
import com.aunt.opeace.ui.theme.WHITE_600
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen() {
    val viewModel: MyPageViewModel = viewModel()
    val activity = (LocalContext.current) as MyPageActivity
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
    viewModel: MyPageViewModel,
    activity: MyPageActivity,
    sheetState: SheetState
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var dialogType by remember { mutableStateOf(MyPageDialogType.LOGOUT) }
    var isShowDialog by remember { mutableStateOf(false) }

    val userInfo = viewModel.state.collectAsState().value.userInfo

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest {
            when (it) {
                Effect.MoveToInfo -> Unit
                Effect.MoveToBlock -> {
                    showBottomSheet = false
                    moveToBlockActivity(activity)
                }

                Effect.Logout -> {
                    showBottomSheet = false
                    isShowDialog = true
                    dialogType = MyPageDialogType.LOGOUT
                }

                Effect.Quit -> {
                    showBottomSheet = false
                    isShowDialog = true
                    dialogType = MyPageDialogType.QUIT
                }
            }
        }
    }

    Content(
        sheetState = sheetState,
        userInfo = userInfo,
        showBottomSheet = showBottomSheet,
        isShowDialog = isShowDialog,
        dialogType = dialogType,
        onSentEvent = viewModel::handleEvent,
        onClickDialogLeftButton = {
            isShowDialog = false
        },
        onClickDialogRightButton = {
            isShowDialog = false
            if (dialogType.isQuit) {
                moveToQuitActivity(activity = activity)
            }

            if (dialogType.isLogout) {
                moveToLogin(activity = activity)
            }
        },
        onChangeBottomSheetState = {
            showBottomSheet = it
        },
        onClickBack = {
            activity.finish()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    sheetState: SheetState,
    userInfo: UserInfo,
    showBottomSheet: Boolean,
    isShowDialog: Boolean,
    dialogType: MyPageDialogType,
    onSentEvent: (Event) -> Unit,
    onClickDialogLeftButton: () -> Unit,
    onClickDialogRightButton: () -> Unit,
    onChangeBottomSheetState: (Boolean) -> Unit,
    onClickBack: () -> Unit
) {
    Scaffold(
        topBar = {
            OPeaceTopBar(
                title = "마이페이지",
                onClickLeftImage = onClickBack
            )
        },
        containerColor = WHITE_600
    ) { contentPadding ->
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    onChangeBottomSheetState(false)
                },
                sheetState = sheetState
            ) {
                SheetContent(
                    onClick = { type ->
                        onSentEvent(Event.OnClickSheetContentType(type = type))
                    }
                )
            }
        }

        if (isShowDialog) {
            MyPageDialog(
                dialogType = dialogType,
                onClickCancel = {
                    onClickDialogLeftButton()
                },
                onClickLogout = {
                    onClickDialogRightButton()
                }
            )
        }

        Column(modifier = Modifier.padding(contentPadding)) {
            MyInfo(
                nickname = userInfo.nickname,
                job = userInfo.job,
                age = userInfo.generation,
                onClickSetting = {
                    onChangeBottomSheetState(true)
                }
            )
            Divider(
                modifier = Modifier.background(Color(0xff303030)),
                thickness = 8.dp
            )
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 30.dp, bottom = 12.dp),
                text = "내가 올린 글",
                color = WHITE,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp
            )
            MyContent()
        }
    }
}

@Composable
private fun MyInfo(
    nickname: String,
    job: String,
    age: String,
    onClickSetting: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(
                top = 20.dp,
                bottom = 24.dp,
                start = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = nickname,
            color = WHITE,
            fontSize = 28.sp,
            fontWeight = FontWeight.W600
        )
        Spacer(modifier = Modifier.width(8.dp))
        TextChip(
            text = job,
            isSelected = false
        )
        Spacer(modifier = Modifier.width(6.dp))
        TextChip(text = age)
        Spacer(modifier = Modifier.width(6.dp))
        Image(
            modifier = Modifier.clickable(onClick = onClickSetting),
            painter = painterResource(id = R.drawable.ic_mypage_setting),
            contentDescription = null
        )
    }
}

@Composable
private fun TextChip(
    text: String,
    isSelected: Boolean = true
) {
    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(40.dp))
            .background(
                if (isSelected) {
                    LIGHTEN
                } else {
                    WHITE_500
                }
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),
        text = text,
        style = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.W500,
            color = if (isSelected) {
                Color_1D1D1D
            } else {
                WHITE
            }
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun MyContent() {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(10) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Blue)
            ) {
                Text(text = "카드가 들어갑니다.")
            }
        }
    }
}

@Composable
private fun SheetContent(
    onClick: (SheetContentClickType) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp)
            .padding(bottom = 27.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        items(count = 4) {
            SheetContentText(
                text = when (it) {
                    0 -> "내 정보 수정"
                    1 -> "차단 관리"
                    2 -> "로그아웃"
                    3 -> "회원탈퇴"
                    else -> ""
                },
                onClick = {
                    onClick(
                        when (it) {
                            0 -> SheetContentClickType.INFO
                            1 -> SheetContentClickType.BLOCK
                            2 -> SheetContentClickType.LOGOUT
                            3 -> SheetContentClickType.QUIT
                            else -> throw Exception("leave application!!")
                        }
                    )
                }
            )
        }
    }
}

@Composable
private fun SheetContentText(
    text: String,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier.clickable(onClick = onClick),
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.W600,
        color = WHITE
    )
}

private fun moveToBlockActivity(activity: MyPageActivity) {
    activity.startActivity(Intent(activity, BlockActivity::class.java))
}

private fun moveToQuitActivity(activity: MyPageActivity) {
    activity.startActivity(Intent(activity, QuitActivity::class.java))
}

private fun moveToLogin(activity: MyPageActivity) {
    activity.startActivity(Intent(activity, LoginActivity::class.java))
}