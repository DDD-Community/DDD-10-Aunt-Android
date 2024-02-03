package com.aunt.opeace.write

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunt.opeace.common.OPeaceButton
import com.aunt.opeace.common.OPeaceTextField
import com.aunt.opeace.common.OPeaceTopBar
import com.aunt.opeace.ui.theme.BLACK
import com.aunt.opeace.ui.theme.Color_303030
import com.aunt.opeace.ui.theme.Color_9D9D9D
import com.aunt.opeace.ui.theme.WHITE_600
import kotlinx.coroutines.delay

@Composable
fun WriteScreen() {
    Content()
}

@Composable
private fun Content() {
    val activity = LocalContext.current as WriteActivity
    val textFieldValue = remember { mutableStateOf(TextFieldValue()) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(true) {
        delay(200)
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE_600)
    ) {
        OPeaceTopBar(
            title = "고민 작성하기",
            onClickLeftImage = {
                activity.finish()
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
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
            title = "고민 올리기",
            enabled = textFieldValue.value.text.length >= 4,
            enabledTextColor = BLACK,
            onClick = {

            }
        )
    }
}