package com.aunt.opeace.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunt.opeace.constants.jobList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobPage() {
    InputPage(title = "직무 선택", subTitle = "직무 계열을 알려주세요") {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            maxItemsInEachRow = 4
        ) {
            val itemModifier = Modifier
                .padding(horizontal = 18.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.DarkGray)

            jobList.forEach {
                Text(
                    modifier = itemModifier,
                    text = it,
                    style = TextStyle(fontSize = 18.sp, fontWeight = W500, color = Color.Black)
                )
            }
        }
    }
}
