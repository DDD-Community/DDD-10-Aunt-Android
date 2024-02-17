package com.aunt.opeace.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.aunt.opeace.ui.theme.Color_1D1D1D
import com.aunt.opeace.ui.theme.Color_303030
import com.aunt.opeace.ui.theme.LIGHTEN
import com.aunt.opeace.ui.theme.WHITE
import com.aunt.opeace.ui.theme.WHITE_500

@Composable
fun OPeaceDialog(
    dialogType: OPeaceDialogType,
    onClickLeftButton: () -> Unit,
    onClickRightButton: () -> Unit
) {
    if (dialogType.isNone) {
        return
    }

    Dialog(onDismissRequest = { onClickLeftButton() }) {
        Surface(
            modifier = Modifier.wrapContentSize(),
            color = Color_303030,
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color_303030)
                    .padding(top = 80.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dialogType.title,
                    color = WHITE,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600
                )
                Row(
                    modifier = Modifier
                        .padding(top = 42.dp)
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = onClickLeftButton)
                            .clip(RoundedCornerShape(100.dp))
                            .background(
                                if (dialogType.isLogout || dialogType.isBlock || dialogType.isDelete) {
                                    WHITE_500
                                } else {
                                    LIGHTEN
                                }
                            )
                            .padding(horizontal = 36.dp, vertical = 16.dp),
                        text = dialogType.leftButtonText,
                        color = if (dialogType.isLogout) {
                            WHITE
                        } else {
                            Color_1D1D1D
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = onClickRightButton)
                            .clip(RoundedCornerShape(100.dp))
                            .background(
                                if (dialogType.isLogout || dialogType.isBlock || dialogType.isDelete) {
                                    LIGHTEN
                                } else {
                                    WHITE_500
                                }
                            )
                            .padding(horizontal = 36.dp, vertical = 16.dp),
                        text = dialogType.rightButtonText,
                        color = if (dialogType.isLogout) {
                            Color_1D1D1D
                        } else {
                            WHITE
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

enum class OPeaceDialogType(
    val title: String = "",
    val leftButtonText: String = "아니요",
    val rightButtonText: String = "네"
) {
    LOGOUT(title = "로그아웃 하시겠어요?", leftButtonText = "아니요", rightButtonText = "로그아웃"),
    QUIT(title = "정말 탈퇴하시겠어요?", rightButtonText = "탈퇴하기"),
    BLOCK(title = "정말 차단하시겠어요?"),
    DELETE(title = "고민을 삭제하시겠어요?"),
    NONE;

    val isLogout: Boolean get() = this == LOGOUT
    val isQuit: Boolean get() = this == QUIT
    val isNone: Boolean get() = this == NONE
    val isBlock: Boolean get() = this == BLOCK
    val isDelete: Boolean get() = this == DELETE
}