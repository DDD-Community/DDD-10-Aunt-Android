package com.aunt.opeace.terms

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunt.opeace.R
import com.aunt.opeace.common.OPeaceButton
import com.aunt.opeace.ui.theme.BLACK
import com.aunt.opeace.ui.theme.Color_1D1D1D
import com.aunt.opeace.ui.theme.Color_9D9D9D
import com.aunt.opeace.ui.theme.WHITE
import com.aunt.opeace.ui.theme.WHITE_300

@Composable
fun TermsScreen() {
    Content()
}

@Composable
private fun Content() {
    var isChipTerms by remember { mutableStateOf(false) }
    var isChipAge by remember { mutableStateOf(false) }
    var isChipService by remember { mutableStateOf(false) }
    var isChipInfo by remember { mutableStateOf(false) }

    LaunchedEffect(
        key1 = isChipAge,
        key2 = isChipService,
        key3 = isChipInfo
    ) {
        isChipTerms = isChipAge && isChipService && isChipInfo
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE)
    ) {
        Title()
        Description(
            modifier = Modifier.weight(1f),
            isSelectedTerms = isChipTerms,
            isSelectedAge = isChipAge,
            isSeletedService = isChipService,
            isSelectedInfo = isChipInfo,
            onClickTermsChip = {
                isChipTerms = isChipTerms.not()
                if (isChipTerms) {
                    isChipAge = true
                    isChipService = true
                    isChipInfo = true
                } else {
                    isChipAge = false
                    isChipService = false
                    isChipInfo = false
                }
            },
            onClickChipAge = {
                isChipAge = isChipAge.not()
            },
            onClickChipService = {
                isChipService = isChipService.not()
            },
            onClickServiceText = {

            },
            onClickChipInfo = {
                isChipInfo = isChipInfo.not()
            },
            onClickInfoText = {

            }
        )
        OPeaceButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 29.dp),
            enabled = isChipTerms,
            containerColor = Color_1D1D1D,
            onClick = {
                // NOTE : 다음 화면으로 이동
            }
        )
    }
}

@Composable
private fun Title() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 125.dp,
                bottom = 66.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "이용 약관 동의",
            color = Color_1D1D1D,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "원활한 서비스 이용을 위해 동의해 주세요",
            color = WHITE_300,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun Description(
    modifier: Modifier = Modifier,
    isSelectedTerms: Boolean,
    isSelectedAge: Boolean,
    isSeletedService: Boolean,
    isSelectedInfo: Boolean,
    onClickTermsChip: () -> Unit,
    onClickChipAge: () -> Unit,
    onClickChipService: () -> Unit,
    onClickServiceText: () -> Unit,
    onClickChipInfo: () -> Unit,
    onClickInfoText: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SelectChipGroup(
            isSelected = isSelectedTerms,
            onClick = onClickTermsChip,
            content = {
                Text(
                    text = "약관 전체 동의",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = WHITE_300
                )
            }
        )
        Divider(
            modifier = Modifier.background(color = Color_9D9D9D),
            thickness = 2.dp
        )
        SelectChipGroup(
            isSelected = isSelectedAge,
            onClick = onClickChipAge,
            content = {
                Text(
                    text = "(필수) 만 14세 이상입니다.",
                    fontSize = 16.sp,
                    color = Color_1D1D1D
                )
            }
        )
        SelectChipGroup(
            isSelected = isSeletedService,
            onClick = onClickChipService,
            content = {
                Text(
                    modifier = Modifier.clickable(onClick = onClickServiceText),
                    text = buildAnnotatedString {
                        append("(필수) 서비스 이용 약관")
                        addStyle(
                            style = SpanStyle(textDecoration = TextDecoration.Underline),
                            start = 5,
                            end = length
                        )
                    },
                    fontSize = 16.sp,
                    color = Color_1D1D1D
                )
            }
        )
        SelectChipGroup(
            isSelected = isSelectedInfo,
            onClick = onClickChipInfo,
            content = {
                Text(
                    modifier = Modifier.clickable(onClick = onClickInfoText),
                    text = buildAnnotatedString {
                        append("(필수) 개인정보 처리방침")
                        addStyle(
                            style = SpanStyle(textDecoration = TextDecoration.Underline),
                            start = 5,
                            end = length
                        )
                    },
                    fontSize = 16.sp,
                    color = Color_1D1D1D
                )
            }
        )
    }
}

@Composable
private fun SelectChipGroup(
    isSelected: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    // NOTE : 여기 디자인에서 height를 알수가 없음...(그래서 정렬 안맞음)
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Chip(isSelected = isSelected, onClick = onClick)
        content()
    }
}

@Composable
private fun Chip(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(21.dp)
            .clickable(onClick = onClick)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = if (isSelected) {
                    BLACK
                } else {
                    Color_9D9D9D
                }
            )
        }
        Image(
            modifier = Modifier.align(alignment = Alignment.Center),
            painter = painterResource(id = R.drawable.ic_v),
            contentDescription = null
        )
    }
}