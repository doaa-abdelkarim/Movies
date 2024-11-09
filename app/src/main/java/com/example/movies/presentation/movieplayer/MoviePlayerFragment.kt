package com.example.movies.presentation.movieplayer

import android.content.pm.ActivityInfo
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.example.movies.databinding.FragmentMoviePlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.ragabz.core.base.BaseVBFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviePlayerFragment : BaseVBFragment<FragmentMoviePlayerBinding, ViewModel>(
    viewBindingInflater = FragmentMoviePlayerBinding::inflate
) {

    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")

    private val args: MoviePlayerFragmentArgs by navArgs()

    override fun initViews() {
        super.initViews()

        initYoutubePlayerView()
    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onPause() {
        super.onPause()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun initYoutubePlayerView() {
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView
            .apply {
//                enterFullScreen()
            }
            .addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(args.clipKey, 0f)
                }
            })
    }

}

