package com.tasnimulhasan.suggestion.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.common.constant.QuranSuggestions
import com.tasnimulhasan.designsystem.theme.BackgroundBlack
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.entity.QuranSuggestion

@Composable
fun SuggestionItem(
    suggestion: QuranSuggestion,
    onSuggestionCLick: (QuranSuggestion) -> Unit,
){
    val isDark = isSystemInDarkTheme()

    ElevatedCard(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) BackgroundBlack else BackgroundWhite
        ),
        onClick = {
            onSuggestionCLick.invoke(suggestion)
        }
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {
            val (title, ayaNo) = createRefs()

            Text(
                modifier = Modifier
                    .constrainAs(title){
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                text = suggestion.suggestionTitle,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground
                )
            )

            Text(
                modifier = Modifier
                    .constrainAs(ayaNo){
                        top.linkTo(title.bottom, margin = 8.dp)
                        start.linkTo(title.start)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                text = suggestion.suggestionAya,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) SaladGreen.copy(alpha = 0.8f) else BottleGreen.copy(alpha = 0.7f)
                ),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSuggestionItem(){
    QuranTheme {
        SuggestionItem(
            suggestion = QuranSuggestions.quranSuggestions.first(),
            onSuggestionCLick = {}
        )
    }
}