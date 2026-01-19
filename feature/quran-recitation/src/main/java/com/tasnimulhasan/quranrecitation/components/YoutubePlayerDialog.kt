package com.tasnimulhasan.quranrecitation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubePlayerDialog(
    surahNumber: Int,
    surahName: String,
    youtubeUrl: String,
    onDismiss: () -> Unit
) {
    val videoId = remember(youtubeUrl) {
        extractYoutubeId(youtubeUrl)
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "$surahNumber. $surahName",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(12.dp))

                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { context ->
                        YouTubePlayerView(context).apply {
                            enableAutomaticInitialization = false

                            val options: IFramePlayerOptions = IFramePlayerOptions.Builder(context)
                                .controls(1)
                                .fullscreen(0)
                                .build()

                            initialize(
                                object : AbstractYouTubePlayerListener() {
                                    override fun onReady(youTubePlayer: YouTubePlayer) {
                                        youTubePlayer.loadVideo(videoId, 0f)
                                    }
                                },
                                true,
                                options
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Close")
                }
            }
        }
    }
}