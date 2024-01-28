package com.aunt.opeace.signup

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.common.OPeaceButton
import com.aunt.opeace.common.OPeaceTopBar
import kotlinx.coroutines.CoroutineScope
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

    Content(
        pagerState = pagerState,
        coroutineScope = coroutineScope,
        keyboardController = keyboardController,
        isValidAge = isValidAge,
        generation = generation,
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
    onSentEvent: (Event) -> Unit
) {
    val isEnabledButton = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val isShowErrorMessage = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

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
        ) { currentPage ->
            when (currentPage) {
                0 -> NicknamePage(
                    focusRequester = focusRequester,
                    onSentNickname = {
                        onSentEvent(Event.SetNickname(it))
                    },
                    onSentButtonEnable = isEnabledButton::value::set,
                    isShowErrorMessage = isShowErrorMessage.value,
                    onSentIsShowErrorMessage = isShowErrorMessage::value::set,
                    errorMessage = errorMessage.value,
                    onSentErrorMessage = errorMessage::value::set
                )

                1 -> AgePage(
                    focusRequester = focusRequester,
                    isValidAge = isValidAge,
                    generation = generation,
                    onSentAge = {
                        onSentEvent(Event.SetAge(it))
                    },
                    onSentButtonEnable = isEnabledButton::value::set
                )

                2 -> JobPage()
            }
        }
        OPeaceButton(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .padding(horizontal = 20.dp),
            enabled = isEnabledButton.value
        ) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(
                    page = when (pagerState.currentPage) {
                        0 -> 1
                        1 -> 2
                        else -> 0 // NOTE : 메인?화면으로 이동 해야함
                    }
                )
            }
            keyboardController?.hide()
        }
    }
}
