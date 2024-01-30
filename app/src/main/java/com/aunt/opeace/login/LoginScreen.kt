package com.aunt.opeace.login

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.R
import com.aunt.opeace.ui.theme.WHITE_300
import com.aunt.opeace.ui.theme.WHITE_600

@Composable
fun LoginScreen() {
    val viewModel: LoginViewModel = viewModel()

    Content(viewModel = viewModel)
}

@Composable
private fun Content(viewModel: LoginViewModel) {
    Content(onSentEvent = viewModel::handleEvent)
}

@Composable
private fun Content(
    onSentEvent: (Event) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WHITE_600)
    ) {
        Image(
            modifier = Modifier.align(alignment = Alignment.Center),
            painter = painterResource(id = R.drawable.ic_login_logo),
            contentDescription = null
        )
        Bottom(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            onClickGoogleLogin = {
                onSentEvent(Event.GoogleLogin)
            },
            onClickKakaoLogin = {
                onSentEvent(Event.KakaoLogin)
            },
            onClickLoginText = {
                onSentEvent(Event.LoginText)
            }
        )
    }
}

@Composable
private fun Bottom(
    modifier: Modifier = Modifier,
    onClickGoogleLogin: () -> Unit,
    onClickKakaoLogin: () -> Unit,
    onClickLoginText: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginLogo(
            onClickGoogleLogin = onClickGoogleLogin,
            onClickKakaoLogin = onClickKakaoLogin
        )
        Text(
            modifier = Modifier
                .padding(
                    top = 30.dp,
                    bottom = 43.dp
                )
                .clickable(onClick = onClickLoginText),
            text = "로그인 없이 둘러보기",
            color = WHITE_300,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            textDecoration = TextDecoration.Underline
        )
    }

}

@Composable
private fun LoginLogo(
    onClickGoogleLogin: () -> Unit,
    onClickKakaoLogin: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(space = 16.dp)) {
        LoginLogo(
            drawableRes = R.drawable.ic_google_login,
            onClick = onClickGoogleLogin
        )
        LoginLogo(
            drawableRes = R.drawable.ic_kakao_login,
            onClick = onClickKakaoLogin
        )
    }
}

@Composable
private fun LoginLogo(
    @DrawableRes drawableRes: Int,
    onClick: () -> Unit
) {
    Image(
        modifier = Modifier.clickable(onClick = onClick),
        painter = painterResource(id = drawableRes),
        contentDescription = null
    )
}