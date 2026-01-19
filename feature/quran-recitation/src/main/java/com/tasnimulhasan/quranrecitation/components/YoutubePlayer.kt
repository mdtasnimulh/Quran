package com.tasnimulhasan.quranrecitation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage

@Composable
fun YoutubeThumbnail(
    youtubeUrl: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val videoId = remember(youtubeUrl) {
        extractYoutubeId(youtubeUrl)
    }

    val thumbnailUrl = remember(videoId) {
        "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = "Youtube Thumbnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Play overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(64.dp)
            )
        }
    }
}

fun extractYoutubeId(url: String): String {
    val uri = url.toUri()

    return when {
        uri.host == "youtu.be" ->
            uri.lastPathSegment ?: ""

        uri.host?.contains("youtube.com") == true ->
            uri.getQueryParameter("v") ?: ""

        else -> ""
    }
}