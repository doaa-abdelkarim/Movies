package com.example.movies.presentation.movieplayer.draft

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.movies.databinding.FragmentMoviePlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviePlayerFragment : Fragment() {

    private var _binding: FragmentMoviePlayerBinding? = null
    private val binding get() = _binding!!
    private val args: MoviePlayerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviePlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

