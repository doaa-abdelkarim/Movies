package com.example.movies.presentation.details.children.clips

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.MoviesApp
import com.example.movies.R
import com.example.movies.databinding.FragmentClipsBinding
import com.example.movies.presentation.details.parent.DetailsFragmentDirections
import com.example.movies.presentation.details.parent.DetailsViewModel
import com.example.movies.presentation.home.UiState
import com.example.movies.presentation.home.children.movies.MoviesFragmentDirections
import com.example.movies.presentation.home.children.tvshows.TVShowsFragmentDirections
import com.example.movies.util.constants.AppConstants.Companion.KEY_STATE_IS_MOVIE
import com.example.movies.util.constants.AppConstants.Companion.KEY_STATE_MOVIE_ID
import com.example.movies.util.exhaustive
import com.ragabz.core.base.BaseVBFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ClipsFragment : BaseVBFragment<FragmentClipsBinding, ClipsViewModel>(
    viewBindingInflater = FragmentClipsBinding::inflate
) {
    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    override val viewModel: ClipsViewModel by viewModels()
    private val detailsViewModel: DetailsViewModel by viewModels({ requireParentFragment() })

    private lateinit var clipsAdapter: ClipsAdapter

    override fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        clipsAdapter = ClipsAdapter(ClipsAdapter.OnItemClickListener {
            viewModel.onClipClick(it)
        })
        binding.apply {
            recyclerViewClipsList.apply {
                adapter = clipsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
    }

    override fun observeState() {
        if ((appContext as MoviesApp).isLargeScreen) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    detailsViewModel.observedMovie.collect {
                        it?.let {
                            viewModel.getMovieClips(
                                observedMovie = it,
                                isLargeScreen = true
                            )
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.clips.collect { uiState ->
                    when (uiState) {
                        is UiState.Initial -> {}

                        is UiState.Loading -> onInProgress()

                        is UiState.Data -> {
                            onRequestSucceeded()
                            clipsAdapter.submitList(uiState.data)
                        }

                        is UiState.Error -> {
                            onRequestFailed(error = uiState.error)
                        }
                    }
                }
            }
        }
    }

    override fun listenToEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.clipsEvent.collect {
                    when (it) {
                        is ClipsEvent.EventNavigateToMoviePlayerScreen -> {
                            if ((appContext as MoviesApp).isLargeScreen)
                                if (findNavController().currentDestination?.id == R.id.moviesFragment)
                                    findNavController().navigate(
                                        MoviesFragmentDirections.actionMoviesFragmentToMoviePlayerFragment(
                                            it.clipKey ?: ""
                                        )
                                    )
                                else
                                    findNavController().navigate(
                                        TVShowsFragmentDirections.actionTvShowsFragmentToMoviePlayerFragment(
                                            it.clipKey ?: ""
                                        )
                                    )
                            else
                                findNavController().navigate(
                                    DetailsFragmentDirections.actionDetailsFragmentToMoviePlayerFragment(
                                        it.clipKey ?: ""
                                    )
                                )
                        }
                    }.exhaustive
                }
            }
        }
    }

    companion object {
        fun newInstance(selectedMovieId: Int, isMovie: Boolean) =
            ClipsFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_STATE_MOVIE_ID, selectedMovieId)
                    putBoolean(KEY_STATE_IS_MOVIE, isMovie)
                }
            }
    }

}