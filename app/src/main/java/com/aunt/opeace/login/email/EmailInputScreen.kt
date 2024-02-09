package com.aunt.opeace.login.email

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.common.OPeaceButton
import com.aunt.opeace.common.OPeaceTextField
import com.aunt.opeace.common.OPeaceTopBar
import com.aunt.opeace.home.HomeActivity
import com.aunt.opeace.signup.SignupActivity
import com.aunt.opeace.ui.theme.WHITE
import com.aunt.opeace.ui.theme.WHITE_600
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmailInputScreen() {
    val viewModel: EmailInputViewModel = viewModel()
    val activity = LocalContext.current as EmailInputActivity
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val emailRequester = remember { FocusRequester() }
    val passwordRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val isLoading = viewModel.state.collectAsState().value.isLoading

    LaunchedEffect(key1 = true) {
        delay(200)
        emailRequester.requestFocus()
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest {
            when (it) {
                is Effect.LoginSuccess -> {
                    keyboardController?.hide()
                    delay(200)
                    activity.startActivity(
                        Intent(
                            activity, if (it.loginType.isCreate) {
                                SignupActivity::class.java
                            } else {
                                HomeActivity::class.java
                            }
                        )
                    )
                    activity.finish()
                }

                is Effect.LoginFail -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WHITE_600)
    ) {
        Column {
            OPeaceTopBar(
                title = "이메일 가입하기",
                onClickLeftImage = {
                    activity.finish()
                }
            )
            Spacer(modifier = Modifier.height(80.dp))
            OPeaceTextField(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .focusRequester(emailRequester),
                value = email.value,
                onValueChange = {
                    email.value = it
                    viewModel.handleEvent(Event.SetEmail(it.text))
                },
                placeHolder = {
                    Text(text = "이메일읍 입력해주세요")
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        passwordRequester.requestFocus()
                    }
                ),
                textStyle = TextStyle(
                    color = WHITE,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OPeaceTextField(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .focusRequester(passwordRequester),
                value = password.value,
                onValueChange = {
                    password.value = it
                    viewModel.handleEvent(Event.SetPassword(it.text))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeHolder = {
                    Text(text = "비밀번호를 입력해주세요")
                },
                textStyle = TextStyle(
                    color = WHITE,
                    fontSize = 18.sp
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            OPeaceButton(
                modifier = Modifier.padding(20.dp),
                title = "가입하기",
                enabled = email.value.text.length >= 4 && password.value.text.length >= 4,
                onClick = {
                    viewModel.handleEvent(Event.OnClickLogin)
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