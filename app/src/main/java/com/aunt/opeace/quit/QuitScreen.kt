package com.aunt.opeace.quit

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.common.OPeaceButton
import com.aunt.opeace.common.OPeaceTextField
import com.aunt.opeace.common.OPeaceTopBar
import com.aunt.opeace.login.LoginActivity
import com.aunt.opeace.ui.theme.BLACK
import com.aunt.opeace.ui.theme.Color_303030
import com.aunt.opeace.ui.theme.Color_9D9D9D
import com.aunt.opeace.ui.theme.WHITE
import com.aunt.opeace.ui.theme.WHITE_600
import kotlinx.coroutines.delay

@Composable
fun QuitScreen() {
    val viewModel: QuitViewModel = viewModel()
    val activity = LocalContext.current as QuitActivity

    Content(
        viewModel = viewModel,
        activity = activity
    )
}

@Composable
private fun Content(
    viewModel: QuitViewModel,
    activity: QuitActivity
) {
    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                Effect.Back -> activity.finish()
                Effect.QuitSuccess -> moveToLogin(activity = activity)
            }
        }
    }

    Content(
        onSentEvent = viewModel::handleEvent
    )
}

@Composable
private fun Content(
    onSentEvent: (Event) -> Unit
) {
    val textFieldValue = remember { mutableStateOf(TextFieldValue()) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(true) {
        delay(200)
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WHITE_600)
    ) {
        OPeaceTopBar(
            title = "탈퇴하기",
            onClickLeftImage = {
                onSentEvent(Event.Back)
            }
        )
        Text(
            modifier = Modifier.padding(start = 28.dp, bottom = 16.dp, top = 28.dp),
            text = "탈퇴 사유를 작성해주세요",
            color = WHITE,
            fontWeight = FontWeight.W700,
            fontSize = 18.sp
        )
        // NOTE : maxHegiht is not working, innerTextField해야하는데....일단 패스
        OPeaceTextField(
            modifier = Modifier
                .padding(horizontal = 28.dp)
                //.clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .heightIn(min = 164.dp, max = 340.dp)
                .background(Color_303030)
                .focusRequester(focusRequester),
            value = textFieldValue.value,
            onValueChange = {
                textFieldValue.value = it
                onSentEvent(Event.SetText(text = it.text))
            },
            singleLine = false,
            maxLines = 5,
            placeHolder = {
                Text(
                    text = "여기를 눌러서 작성해주세요",
                    color = Color_9D9D9D,
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp
                )
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        OPeaceButton(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .padding(horizontal = 28.dp),
            title = "완료",
            enabled = textFieldValue.value.text.length >= 4,
            enabledTextColor = BLACK,
            onClick = {
                onSentEvent(Event.OnClickDone)
            }
        )
    }
}

private fun moveToLogin(activity: QuitActivity) {
    activity.startActivity(
        Intent(activity, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    )
}