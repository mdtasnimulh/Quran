package com.tasnimulhasan.designsystem.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.R as Res

@Composable
fun MainTopAppBar(
    appBarTitle: String,
    onBackClick: () -> Unit,
    navigationIcon: ImageVector,
    isMenuIconVisible: Boolean = false,
    onMenuIconClick: () -> Unit,
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = navigationIcon,
                contentDescription = context.getString(Res.string.desc_top_app_bar_back_icon),
                tint = BackgroundWhite
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
                color = BackgroundWhite,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            maxLines = 1,
        )

        if (!isMenuIconVisible) Spacer(Modifier.width(40.dp))

        if (isMenuIconVisible) {
            IconButton(onClick = onMenuIconClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = context.getString(Res.string.desc_top_app_bar_back_icon),
                    tint = BackgroundWhite
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomTopAppBar() {
    MainTopAppBar(
        appBarTitle = "Top App Bar",
        onBackClick = {},
        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
        isMenuIconVisible = false,
        onMenuIconClick = {}
    )
}