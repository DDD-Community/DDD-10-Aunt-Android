package com.aunt.opeace.write

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.common.OPeaceButton
import com.aunt.opeace.common.OPeaceTextField
import com.aunt.opeace.common.OPeaceTopBar
import com.aunt.opeace.home.HomeActivity
import com.aunt.opeace.ui.theme.ALERT
import com.aunt.opeace.ui.theme.BLACK
import com.aunt.opeace.ui.theme.Color_303030
import com.aunt.opeace.ui.theme.Color_9D9D9D
import com.aunt.opeace.ui.theme.WHITE
import com.aunt.opeace.ui.theme.WHITE_200
import com.aunt.opeace.ui.theme.WHITE_400
import com.aunt.opeace.ui.theme.WHITE_600
import kotlinx.coroutines.delay

@Composable
fun WriteScreen() {
    val viewModel: WriteViewModel = viewModel()
    val activity = LocalContext.current as WriteActivity

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                Effect.UploadSuccess -> {
                    showMessage(activity = activity, "고민이 추가되었습니다")
                    moveToHome(activity = activity)
                    activity.finish()
                }

                Effect.UploadFail -> {
                    showMessage(activity = activity, "업로드에 실패했습니다")
                }
            }
        }
    }

    Content(viewModel = viewModel)
}

@Composable
private fun Content(
    viewModel: WriteViewModel
) {
    val isLoading = viewModel.state.collectAsState().value.isLoading

    Content(
        isLoading = isLoading,
        onSentEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Content(
    isLoading: Boolean,
    onSentEvent: (Event) -> Unit
) {
    val activity = LocalContext.current as WriteActivity
    val keyboardController = LocalSoftwareKeyboardController.current
    val textFieldValue = remember { mutableStateOf(TextFieldValue()) }
    val firstAnswer = remember { mutableStateOf(TextFieldValue()) }
    val secondAnswer = remember { mutableStateOf(TextFieldValue()) }
    val focusRequester = remember { FocusRequester() }
    val answerRequester = remember { FocusRequester() }
    val showAnswer = remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        delay(200)
        focusRequester.requestFocus()
    }

    LaunchedEffect(key1 = showAnswer.value) {
        if (firstAnswer.value.text.isBlank() && showAnswer.value) {
            delay(200)
            answerRequester.requestFocus()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE_600)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            OPeaceTopBar(
                title = if (showAnswer.value) {
                    "답변 작성하기"
                } else {
                    "고민 작성하기"
                },
                onClickLeftImage = {
                    activity.finish()
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            if (showAnswer.value) {
                BasicTextField(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .focusRequester(answerRequester),
                    value = firstAnswer.value,
                    onValueChange = {
                        if (it.text.length <= 17) {
                            firstAnswer.value = it
                            onSentEvent(Event.SetFirstAnswer(it.text))
                        }
                    },
                    maxLines = 1,
                    singleLine = true,
                    decorationBox = {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100.dp))
                                .background(WHITE_400)
                                .fillMaxWidth()
                                .padding(all = 16.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.TopStart),
                                text = "A",
                                color = WHITE_200,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600
                            )
                            if (firstAnswer.value.text.isBlank()) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = "내용을 입력해 주세요",
                                    color = WHITE_200,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W600
                                )
                            }
                            Row(modifier = Modifier.padding(start = 32.dp)) {
                                it()
                            }
                        }
                    }
                )
                BasicTextField(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .padding(horizontal = 40.dp),
                    value = secondAnswer.value,
                    onValueChange = {
                        if (it.text.length <= 17) {
                            secondAnswer.value = it
                            onSentEvent(Event.SetSecondAnswer(it.text))
                        }
                    },
                    maxLines = 1,
                    singleLine = true,
                    decorationBox = {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100.dp))
                                .background(WHITE_400)
                                .fillMaxWidth()
                                .padding(all = 16.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.TopStart),
                                text = "B",
                                color = WHITE_200,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600
                            )
                            if (secondAnswer.value.text.isBlank()) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = "내용을 입력해 주세요",
                                    color = WHITE_200,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W600
                                )
                            }
                            Row(modifier = Modifier.padding(start = 32.dp)) {
                                it()
                            }
                        }
                    }
                )
            } else {
                OPeaceTextField(
                    modifier = Modifier
                        .padding(horizontal = 28.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .heightIn(min = 164.dp, max = 340.dp)
                        .background(Color_303030)
                        .focusRequester(focusRequester),
                    value = textFieldValue.value,
                    onValueChange = {
                        textFieldValue.value = it
                        onSentEvent(Event.SetText(it.text))
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
                    },
                    textStyle = TextStyle(
                        color = WHITE,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = "${textFieldValue.value.text.length}/60",
                    color = if (textFieldValue.value.text.length <= 60) {
                        Color_9D9D9D
                    } else {
                        ALERT
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            OPeaceButton(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .padding(horizontal = 28.dp),
                title = if (showAnswer.value) {
                    "고민 올리기"
                } else {
                    "다음"
                },
                enabled = if (showAnswer.value) {
                    firstAnswer.value.text.length >= 2 && secondAnswer.value.text.length >= 2
                } else {
                    textFieldValue.value.text.length in 4..60
                },
                enabledTextColor = BLACK,
                onClick = {
                    if (showAnswer.value) {
                        onSentEvent(Event.OnClickButton)
                    } else {
                        keyboardController?.hide()
                        showAnswer.value = true
                    }
                }
            )
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp),
                color = WHITE
            )
        }
    }
}

private fun moveToHome(activity: WriteActivity) {
    activity.startActivity(
        Intent(activity, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    )
}

private fun showMessage(activity: WriteActivity, message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}