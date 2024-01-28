package com.aunt.opeace.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunt.opeace.common.OPeaceButton
import com.aunt.opeace.common.OPeaceErrorText
import com.aunt.opeace.common.OPeaceTextField
import kotlinx.coroutines.delay

@Composable
fun AgePage(
    title: String = "나이 입력",
    subTitle: String = "출생 연도를 알려주세요",
    isValidAge: Boolean,
    generation: String,
    onSentAge: (String) -> Unit,
    onClickNextButton: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val age = remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(true) {
        delay(200)
        focusRequester.requestFocus()
    }

    InputPage(title = title, subTitle = subTitle) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(65.dp))
                OPeaceTextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    value = age.value,
                    onValueChange = {
                        if (it.text.length <= 4) {
                            age.value = it
                            onSentAge(it.text)
                        }
                    },
                    textStyle = TextStyle(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.W700
                    ).copy(textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    placeHolder = {
                        AgePlaceholder()
                    }
                )
                if (generation.isNotBlank() && isValidAge) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = generation
                    )
                }
                if (isValidAge.not()) {
                    OPeaceErrorText(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "정확한 연도를 입력해 주세요"
                    )
                }
            }
            OPeaceButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .padding(horizontal = 20.dp),
                enabled = generation.isNotBlank() && isValidAge,
                onClick = onClickNextButton
            )
        }
    }
}

@Composable
private fun AgePlaceholder() {
    Text(
        text = "YYYY",
        style = TextStyle(
            fontSize = 48.sp,
            fontWeight = FontWeight.W700,
            textAlign = TextAlign.Center,
            color = Color.Gray,
        )
    )
}

