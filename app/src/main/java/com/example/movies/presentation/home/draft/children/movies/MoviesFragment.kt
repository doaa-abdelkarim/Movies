package com.example.movies.presentation.home.draft.children.movies

import androidx.fragment.app.viewModels
import com.example.movies.presentation.home.draft.base.VideosFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : VideosFragment<MoviesViewModel>() {

    override val videosViewModel: MoviesViewModel by viewModels()

    companion object {
        fun newInstance() = MoviesFragment()
    }

}