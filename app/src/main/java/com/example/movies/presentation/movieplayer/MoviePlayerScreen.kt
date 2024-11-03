package com.example.movies.presentation.movieplayer

import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.movies.util.extensions.findActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun MoviePlayerScreen(clipKey: String) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val youTubePlayerView = remember {
        YouTubePlayerView(context).apply {
            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(clipKey, 0f)
                }
            })
        }
    }
    DisposableEffect(lifecycleOwner) {
        val activity = context.findActivity()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        lifecycleOwner.lifecycle.addObserver(youTubePlayerView)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(youTubePlayerView)
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
    AndroidView(factory = { youTubePlayerView })
}