package com.example.movies.presentation.home.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.MoviesApp
import com.example.movies.R
import com.example.movies.presentation.common.LoaderStateAdapter
import com.example.movies.presentation.common.MoviesAdapter
import com.example.movies.presentation.home.parent.HomeFragmentDirections
import com.example.movies.util.exhaustive
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class VideosFragment<VM : BaseVideosViewModel> : Fragment(R.layout.fragment_videos) {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private lateinit var loader: View
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var loaderStateAdapter: LoaderStateAdapter
    abstract val videosViewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view = view)
        observeState()
        listenToEvents()
    }

    private fun initViews(view: View) {
        initLoader(view = view)
        initRecyclerView(view)
        //  initFragmentContainerView(view, savedInstanceState)
    }

    private fun initLoader(view: View) {
        loader = view.findViewById<ConstraintLayout>(R.id.loader)
    }

    private fun initRecyclerView(view: View) {
        if (!::moviesAdapter.isInitialized) {
            moviesAdapter = MoviesAdapter(
                MoviesAdapter.OnItemClickListener {
                    videosViewModel.onVideoClick(it)
                }
            ).apply {
                addLoadStateListener { loadStates ->
                    if ((appContext as MoviesApp).isLargeScreen)
                        if (videosViewModel.observedVideo.value == null &&
                            loadStates.refresh is LoadState.NotLoading && itemCount > 0
                        ) {
                            videosViewModel.updateObservedVideo(video = peek(0))
                        }

                    if (loadStates.refresh is LoadState.Loading) {
                        onInProgress()
                    } else if (loadStates.refresh is LoadState.Error) {
                        onRequestFailed(
                            error = (loadStates.refresh as LoadState.Error).error
                        )
                    }
                    if (loadStates.refresh is LoadState.NotLoading) {
                        onRequestCompleted()
                    }
                }
            }
            loaderStateAdapter = LoaderStateAdapter { moviesAdapter.retry() }
        }

        view.findViewById<RecyclerView>(R.id.recycler_view_movies).apply {
            adapter = moviesAdapter.withLoadStateFooter(loaderStateAdapter)
            layoutManager = GridLayoutManager(requireContext(), 4)
            setHasFixedSize(true)
        }
    }

    /*    private fun initFragmentContainerView(view: View, savedInstanceState: Bundle?) {
            if (savedInstanceState == null)
                view.findViewById<FragmentContainerView>(R.id.fragment_details)
                    ?.let {
                        childFragmentManager.beginTransaction()
                            .add(R.id.fragment_details, DetailsFragment.newInstance())
                            .commit()
                    }
        }*/

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                videosViewModel.videosFlow
                    .distinctUntilChanged()
                    .collectLatest {
                        moviesAdapter.submitData(it)
                    }
            }
        }
    }

    private fun listenToEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                videosViewModel.videosEvent.collect {
                    when (it) {
                        is VideosEvent.NavigateToDetailsScreen ->
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                                    movieId = it.video.id,
                                    isMovie = it.video.isMovie
                                )
                            )
                    }.exhaustive
                }
            }
        }
    }

    private fun onInProgress() {
        loader.visibility = View.VISIBLE
    }

    private fun onRequestSucceeded() {
        onRequestCompleted()
    }

    private fun onRequestFailed(error: Throwable) {
        onRequestCompleted()
        Toast
            .makeText(
                context,
                error.localizedMessage ?: getString(
                    R.string.unknown_error
                ),
                Toast.LENGTH_LONG
            )
            .show()
    }

    private fun onRequestCompleted() {
        loader.visibility = View.GONE
    }

}