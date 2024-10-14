package com.example.movies.presentation.home.children.tvshows

import androidx.fragment.app.viewModels
import com.example.movies.presentation.home.base.VideosFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TVShowsFragment : VideosFragment<TVShowsViewModel>() {

    override val videosViewModel: TVShowsViewModel by viewModels()

    companion object {
        fun newInstance() = TVShowsFragment()
    }
}