package com.aunt.opeace.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunt.opeace.common.OPeaceButton
import com.aunt.opeace.common.OPeaceErrorText
import com.aunt.opeace.common.OPeaceTextField
import kotlinx.coroutines.delay

@Composable
fun NicknamePage(
    title: String = "닉네임 입력",
    subTitle: String = "2~5자까지 입력할 수 있어요",
    onSentNickname: (String) -> Unit,
    onClickNextButton: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val textFieldValue = remember { mutableStateOf(TextFieldValue()) }
    val isShowErrorMessage = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    LaunchedEffect(true) {
        delay(200)
        focusRequester.requestFocus()
    }

    InputPage(title = title, subTitle = subTitle) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(65.dp))
                OPeaceTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester),
                    value = textFieldValue.value,
                    onValueChange = {
                        checkNicknameWithAction(
                            nickname = it.text,
                            isShowErrorMessage = isShowErrorMessage::value::set,
                            errorMessage = errorMessage::value::set
                        )
                        if (it.text.length <= 6) {
                            textFieldValue.value = it
                            onSentNickname(it.text)
                        }
                    },
                    textStyle = TextStyle(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.W700,
                        color = Color.Black
                    ).copy(textAlign = TextAlign.Center),
                    placeHolder = {
                        Text(
                            text = "빵미",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.W700,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                if (isShowErrorMessage.value) {
                    OPeaceErrorText(text = errorMessage.value)
                }
            }
            OPeaceButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .padding(horizontal = 20.dp),
                enabled = textFieldValue.value.text.length in (2..5), // NOTE : 서버 통신 후 처리
                onClick = onClickNextButton
            )
        }
    }
}

private fun checkNicknameWithAction(
    nickname: String,
    isShowErrorMessage: (Boolean) -> Unit,
    errorMessage: (String) -> Unit
) {
    if (nickname.length > 1 && (isValidNickname(input = nickname) || nickname.length >= 6)) {
        isShowErrorMessage(true)
        errorMessage(
            when {
                isValidNickname(input = nickname) -> "띄어쓰기와 특수문자는 사용할 수 없어요"
                nickname.length >= 6 -> "닉네임은 5글자 이하까지 입력 가능해요"
                else -> ""
            }
        )
    } else {
        isShowErrorMessage(false)
        errorMessage("")
    }
}

private fun isValidNickname(input: String): Boolean {
    val regex = Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\",./<>?\\\\|\\s]+")
    return regex.containsMatchIn(input)
}



