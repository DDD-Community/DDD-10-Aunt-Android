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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunt.opeace.common.OPeaceButton
import com.aunt.opeace.common.OPeaceTopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignupScreen() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    val viewModel: SignupViewModel = viewModel()

    Content(
        pagerState = pagerState,
        coroutineScope = coroutineScope,
        onSentEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Content(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    onSentEvent: (Event) -> Unit
) {
    val isEnabledButton = remember { mutableStateOf(false) }

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
                    onSentButtonEnable = isEnabledButton::value::set
                )

                1 -> AgePage()
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
        }
    }
}
