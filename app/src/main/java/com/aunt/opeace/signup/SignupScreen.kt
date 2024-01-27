package com.aunt.opeace.signup

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aunt.opeace.common.OPeaceButton
import com.aunt.opeace.common.OPeaceTopBar


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignupScreen() {
    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        OPeaceTopBar(
            title = "",
            leftImageResId = 0,
            rightImageResId = 0
        )

        HorizontalPager(state = pagerState) {

        }

        OPeaceButton(
            title = "다음",
            enable = true,
            onClick = {}
        )
    }
}
