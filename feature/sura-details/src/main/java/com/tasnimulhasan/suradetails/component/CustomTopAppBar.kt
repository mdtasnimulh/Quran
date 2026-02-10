package com.tasnimulhasan.suradetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.R as Res
import androidx.compose.ui.res.stringResource

@Composable
fun CustomTopAppBar(
    appBarTitle: String,
    onBackClick: () -> Unit,
    isMenuIconVisible: Boolean = false,
    onMenuIconClick: () -> Unit,
) {
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.desc_top_app_bar_back_icon),
                tint = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(1f)
                .padding(horizontal = 8.dp),
            text = appBarTitle,
            style = TextStyle(
                fontSize = 18.sp,
                color = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            ),
            maxLines = 1,
        )

        if (!isMenuIconVisible) Spacer(Modifier.width(40.dp))

        if (isMenuIconVisible) {
            IconButton(onClick = onMenuIconClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(Res.string.desc_top_app_bar_back_icon),
                    tint = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomTopAppBar() {
    CustomTopAppBar(
        appBarTitle = "Top App Bar",
        onBackClick = {},
        isMenuIconVisible = false,
        onMenuIconClick = {}
    )
}