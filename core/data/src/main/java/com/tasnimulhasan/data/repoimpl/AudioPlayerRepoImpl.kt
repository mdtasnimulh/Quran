package com.tasnimulhasan.data.repoimpl

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.tasnimulhasan.domain.repository.AudioPlayerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlayerRepoImpl @Inject constructor(
    @ApplicationContext context: Context
) : AudioPlayerRepository {

    private val player: ExoPlayer = ExoPlayer.Builder(context).build().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(C.AUDIO_CONTENT_TYPE_SPEECH)
                .setUsage(C.USAGE_MEDIA)
                .build(),
            true
        )
    }

    override fun playRaw(resId: Int) {
        val uri = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .path(resId.toString())
            .build()

        val mediaItem = MediaItem.fromUri(uri)
        player.apply {
            stop()
            clearMediaItems()
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    override fun stop() {
        player.stop()
    }

    override fun release() {
        player.release()
    }
}
