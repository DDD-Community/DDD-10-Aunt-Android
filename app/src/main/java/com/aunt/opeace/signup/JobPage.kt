package com.aunt.opeace.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunt.opeace.color.LIGHTEN_0
import com.aunt.opeace.color.WHITE_400
import com.aunt.opeace.common.OPeaceButton
import com.aunt.opeace.constants.jobList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobPage(
    title: String = "직무 선택",
    subTitle: String = "직무 계열을 알려주세요",
    job: String,
    onClick: (String) -> Unit,
    onClickNextButton: () -> Unit
) {
    val isSelectedJob = remember { mutableStateOf("") }

    InputPage(title = title, subTitle = subTitle) {
        Box(modifier = Modifier.fillMaxSize()) {
            FlowRow(
                modifier = Modifier
                    .padding(top = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                maxItemsInEachRow = 4
            ) {
                jobList.forEach {
                    TextChip(
                        text = it,
                        isSelected = job == it,
                        onClick = {
                            onClick(it)
                            isSelectedJob.value = it
                        }
                    )
                }
            }
            OPeaceButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .padding(horizontal = 20.dp),
                enabled = job.isNotBlank(),
                onClick = onClickNextButton
            )
        }
    }
}

@Composable
private fun TextChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(40.dp))
            .background(
                if (isSelected) {
                    LIGHTEN_0
                } else {
                    WHITE_400
                }
            )
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
            .clickable(onClick = onClick),
        text = text,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = W500,
            color = Color.Black
        ),
        textAlign = TextAlign.Center
    )
}