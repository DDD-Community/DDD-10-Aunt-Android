package com.aunt.opeace.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobPage(
    jobList: List<String> = listOf(
        "경영",
        "광고",
        "기획",
        "개발",
        "데이터",
        "디자인",
        "마케팅",
        "방송",
        "운영",
        "이커머스",
        "회계",
        "인사",
        "영업",
        "물류",
        "연구",
        "의료",
        "제약",
        "엔지니어링",
        "생산품질",
        "교육",
        "법률",
        "공공",
        "서비스",
        "기타",
    ),
) {
    InputPage(title = "직무 선택", subTitle = "직무 계열을 알려주세요") {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            maxItemsInEachRow = 4
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            val itemModifier = Modifier
                .padding(horizontal = 18.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.DarkGray)

            repeat(jobList.size) {
                Text(
                    modifier = itemModifier,
                    text = jobList[it],
                    style = TextStyle(fontSize = 18.sp, fontWeight = W500, color = Color.Black)
                )
            }
        }
    }
}
