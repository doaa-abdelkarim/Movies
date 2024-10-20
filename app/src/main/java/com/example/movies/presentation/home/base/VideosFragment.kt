package com.example.movies.presentation.home.base

import android.content.Context
import android.os.Bundle
import android.view.View
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
import com.example.movies.util.AppConstants.Companion.KEY_STATE_SELECTED_VIDEO
import com.example.movies.util.exhaustive
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

//CodeReview swipe to refresh is not implemented which is required in "add infinite scrolling" task
//CodeReview i think there's a problem in progress bar behavior, it doesn't always show when it should
abstract class VideosFragment<VM : VideosViewModel> : Fragment(R.layout.fragment_videos) {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private lateinit var videosAdapter: VideosAdapter
    abstract val videosViewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
//        initFragmentContainerView(view)
        observeState()
        listenToEvents()
    }

    private fun initRecyclerView(view: View) {
        if (!::videosAdapter.isInitialized)
            videosAdapter = VideosAdapter(
                VideosAdapter.OnItemClickListener {
                    videosViewModel.onVideoClicked(it)
                }
            ).apply {
                if ((appContext as MoviesApp).isLargeScreen)
                    addLoadStateListener { loadStates ->
                        if (videosViewModel.selectedVideo.value == null &&
                            loadStates.refresh is LoadState.NotLoading && itemCount > 0
                        ) {
                            videosViewModel.selectedVideo.value = peek(0)
                        }
                    }
            }

        view.findViewById<RecyclerView>(R.id.recycler_view_videos_list).apply {
            adapter = videosAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
            setHasFixedSize(true)
        }
    }

    /*
    I found that the its viewmodel is recreated with it after configuration change so I added it
    statically in XML file for now. I will search for another solution later
     */
//    private fun initFragmentContainerView(view: View) {
//        view.findViewById<FragmentContainerView>(R.id.fragment_details)
//            ?.let {
//                childFragmentManager.beginTransaction()
//                    .add(R.id.fragment_details, DetailsFragment.newInstance())
//                    .commit()
//            }
//    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                videosViewModel.videosFlow
                    .distinctUntilChanged()
                    .collectLatest {
                        videosAdapter.submitData(it)
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
                                R.id.detailsFragment,
                                Bundle().apply {
                                    putParcelable(KEY_STATE_SELECTED_VIDEO, it.video)
                                }
                            )
                    }.exhaustive
                }
            }
        }
    }

}