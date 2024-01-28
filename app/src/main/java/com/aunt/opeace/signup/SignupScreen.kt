package com.aunt.opeace.signup

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.common.OPeaceTopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SignupScreen() {
    val viewModel: SignupViewModel = viewModel()
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Content(
        viewModel = viewModel,
        pagerState = pagerState,
        coroutineScope = coroutineScope,
        keyboardController = keyboardController
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun Content(
    viewModel: SignupViewModel,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    keyboardController: SoftwareKeyboardController?,
) {
    val isValidAge: Boolean = viewModel.state.collectAsState().value.isValidAge
    val generation: String = viewModel.state.collectAsState().value.generation
    val job: String = viewModel.state.collectAsState().value.job

    Content(
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
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    keyboardController: SoftwareKeyboardController?,
    isValidAge: Boolean,
    generation: String,
    job: String,
    onSentEvent: (Event) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OPeaceTopBar()
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
            userScrollEnabled = false
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
                            // NOTE : 다음 화면으로 이동
                        }
                    )
                }
            }
        }
    }
}