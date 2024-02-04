package com.aunt.opeace.signup

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.common.OPeaceTopBar
import com.aunt.opeace.home.HomeActivity
import com.aunt.opeace.ui.theme.WHITE_600
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SignupScreen() {
    val viewModel: SignupViewModel = viewModel()
    val activity = LocalContext.current as SignupActivity
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler {
        moveToPreviousPage(
            activity = activity,
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            keyboardController = keyboardController
        )
    }

    Content(
        viewModel = viewModel,
        activity = activity,
        pagerState = pagerState,
        coroutineScope = coroutineScope,
        keyboardController = keyboardController
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun Content(
    viewModel: SignupViewModel,
    activity: SignupActivity,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    keyboardController: SoftwareKeyboardController?,
) {
    val isValidAge: Boolean = viewModel.state.collectAsState().value.isValidAge
    val generation: String = viewModel.state.collectAsState().value.generation
    val job: String = viewModel.state.collectAsState().value.job

    Content(
        activity = activity,
        pagerState = pagerState,
        coroutineScope = coroutineScope,
        keyboardController = keyboardController,
        isValidAge = isValidAge,
        generation = generation,
        job = job,
        onSentEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun Content(
    activity: SignupActivity,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    keyboardController: SoftwareKeyboardController?,
    isValidAge: Boolean,
    generation: String,
    job: String,
    onSentEvent: (Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WHITE_600),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OPeaceTopBar(
            onClickLeftImage = {
                moveToPreviousPage(
                    activity = activity,
                    pagerState = pagerState,
                    coroutineScope = coroutineScope,
                    keyboardController = keyboardController
                )
            }
        )
        Spacer(modifier = Modifier.height(27.dp))
        SignupIndicator(
            currentPage = pagerState.currentPage,
            maxPage = pagerState.pageCount
        )
        Spacer(modifier = Modifier.height(44.dp))
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            verticalAlignment = Alignment.Top,
            userScrollEnabled = false,
        ) { currentPage ->
            when (currentPage) {
                0 -> NicknamePage(
                    onSentNickname = {
                        onSentEvent(Event.SetNickname(it))
                    },
                    onClickNextButton = {
                        coroutineScope.launch {
                            keyboardController?.hide()
                            delay(200)
                            pagerState.animateScrollToPage(page = 1)
                        }
                    }
                )

                1 -> AgePage(
                    isValidAge = isValidAge,
                    generation = generation,
                    onSentAge = {
                        onSentEvent(Event.SetAge(it))
                    },
                    onClickNextButton = {
                        coroutineScope.launch {
                            keyboardController?.hide()
                            delay(200)
                            pagerState.animateScrollToPage(page = 2)
                        }
                    }
                )

                2 -> {
                    JobPage(
                        job = job,
                        onClick = {
                            onSentEvent(Event.OnClickJob(it))
                        },
                        onClickNextButton = {
                            moveToHome(activity = activity)
                        }
                    )
                }
            }
        }
    }
}

// TODO : 마지막 page에서 뒤로가기 클릭 시 이전 화면으로 이동했다가 제일 처음 화면으로 이동하는 문제가 있음
@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
private fun moveToPreviousPage(
    activity: SignupActivity,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    keyboardController: SoftwareKeyboardController?
) {
    when (pagerState.currentPage) {
        0 -> activity.finish()
        1 -> coroutineScope.launch {
            keyboardController?.hide()
            pagerState.animateScrollToPage(page = 0)
        }

        2 -> coroutineScope.launch {
            pagerState.animateScrollToPage(page = 1)
        }
    }
}

private fun moveToHome(activity: SignupActivity) {
    activity.startActivity(Intent(activity, HomeActivity::class.java))
}