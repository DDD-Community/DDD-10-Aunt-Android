package com.aunt.opeace.login

import android.content.Intent
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.R
import com.aunt.opeace.home.HomeActivity
import com.aunt.opeace.login.email.EmailInputActivity
import com.aunt.opeace.ui.theme.WHITE_300
import com.aunt.opeace.ui.theme.WHITE_600
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen() {
    val viewModel: LoginViewModel = viewModel()

    Content(viewModel = viewModel)
}

@Composable
private fun Content(viewModel: LoginViewModel) {
    //val context = LocalContext.current as LoginInterface
    val activity = LocalContext.current as LoginActivity
    val nickname = viewModel.state.collectAsState().value.nickname

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest {
            when (it) {
                Effect.GoogleLogin,
                Effect.KakaoLogin -> Unit

                Effect.MoveToMain -> moveToHome(activity = activity)
                Effect.MoveToEmailInput -> moveToEmailInput(activity = activity)
            }
        }
    }

    Content(nickname = nickname, onSentEvent = viewModel::handleEvent)
}

@Composable
private fun Content(
    nickname: String,
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
            nickname = nickname,
            onClickGoogleLogin = {
                onSentEvent(Event.OnClickType(type = ClickType.GOOGLE))
            },
            onClickKakaoLogin = {
                onSentEvent(Event.OnClickType(type = ClickType.KAKAO))
            },
            onClickLoginText = {
                onSentEvent(Event.OnClickType(type = ClickType.TEXT))
            },
            onClickEmailSignup = {
                onSentEvent(Event.OnClickType(type = ClickType.EMAIL))
            }
        )
    }
}

@Composable
private fun Bottom(
    modifier: Modifier = Modifier,
    nickname: String,
    onClickGoogleLogin: () -> Unit,
    onClickKakaoLogin: () -> Unit,
    onClickLoginText: () -> Unit,
    onClickEmailSignup: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        LoginLogo(
//            onClickGoogleLogin = onClickGoogleLogin,
//            onClickKakaoLogin = onClickKakaoLogin
//        )
        // 서버 구현 안되서 그냥 파이버페이스 사용
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clickable(onClick = onClickEmailSignup),
            text = if (nickname.isBlank()) {
                "이메일로 가입하기"
            } else {
                "로그인"
            },
            color = WHITE_300,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            textDecoration = TextDecoration.Underline
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

private fun moveToEmailInput(activity: LoginActivity) {
    activity.startActivity(Intent(activity, EmailInputActivity::class.java))
}

private fun moveToHome(activity: LoginActivity) {
    activity.startActivity(Intent(activity, HomeActivity::class.java))
}