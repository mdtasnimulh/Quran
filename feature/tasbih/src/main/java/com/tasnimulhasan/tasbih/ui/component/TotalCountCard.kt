package com.tasnimulhasan.tasbih.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.entity.tasbih.DhikrCountEntity

@Composable
fun TotalCountCard(
    dhikrCount: DhikrCountEntity,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFE6F1EA),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        /* Title */
        Text(
            text = "Total Dhikr Count",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = BottleGreen,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        /* Total Count */
        Text(
            text = dhikrCount.totalCount.toString(),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = BottleGreen,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* Individual Counts */
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DhikrCountRow("Alhamdulillah", dhikrCount.alhamdulillah)
            DhikrCountRow("Subhan Allah", dhikrCount.subhanAllah)
            DhikrCountRow("Allahu Akbar", dhikrCount.allahuAkbar)
            DhikrCountRow("La Ilaha Illallah", dhikrCount.laIlahaIllallah)
        }
    }
}

@Composable
private fun DhikrCountRow(
    name: String,
    count: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            fontSize = 13.sp,
            color = Color.Black.copy(alpha = 0.7f)
        )
        Text(
            text = count.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = BottleGreen
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTotalCountCard() {
    QuranTheme {
        TotalCountCard(
            dhikrCount = DhikrCountEntity(
                alhamdulillah = 150,
                subhanAllah = 200,
                allahuAkbar = 180,
                laIlahaIllallah = 120,
                totalCount = 650
            )
        )
    }
}