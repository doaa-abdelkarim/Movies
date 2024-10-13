package com.example.movies.presentation.videoplayer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class VideoPlayerViewModel @Inject constructor(
    private val state: SavedStateHandle
) :
    ViewModel() {

    val clipKeyArg = state.get<String>("clipKey")

}