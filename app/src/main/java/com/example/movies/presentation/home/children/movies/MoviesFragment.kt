package com.example.movies.presentation.home.children.movies

import androidx.fragment.app.viewModels
import com.example.movies.presentation.home.base.VideosFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : VideosFragment<MoviesViewModel>() {

    override val videosViewModel: MoviesViewModel by viewModels()

    companion object {
        fun newInstance() = MoviesFragment()
    }

}