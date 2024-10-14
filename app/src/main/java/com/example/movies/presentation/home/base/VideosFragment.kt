package com.example.movies.presentation.home.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.MoviesApp
import com.example.movies.R
import com.example.movies.presentation.details.parent.DetailsFragment
import com.example.movies.util.EndlessRecyclerViewScrollListener
import com.example.movies.util.exhaustive
import timber.log.Timber

//CodeReview swipe to refresh is not implemented which is required in "add infinite scrolling" task
//CodeReview i think there's a problem in progress bar behavior, it doesn't always show when it should
abstract class VideosFragment<VM: VideosViewModel> : Fragment(R.layout.fragment_videos_list) {

    private lateinit var videosAdapter: VideosAdapter
    abstract val videosViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMoviesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<FragmentContainerView>(R.id.fragment_details)?.let { insertNestedFragment() }
        initRecyclerView(view)
        subscribeToLiveData()
        subscribeToFlow()
    }

    private fun insertNestedFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_details, DetailsFragment.newInstance())
            .commit()
    }

    private fun initMoviesAdapter() {
        videosAdapter = VideosAdapter(
            VideosAdapter.OnItemClickListener {
                videosViewModel.onVideoClicked(it)
            }
        )
    }

    private fun initRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.recycler_view_videos_list).apply {
            adapter = videosAdapter
            val layoutManager = GridLayoutManager(requireContext(), 4)
            this.layoutManager = layoutManager
            addOnScrollListener(object :
                EndlessRecyclerViewScrollListener(layoutManager, videosViewModel.nextPage) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    Handler(Looper.getMainLooper())
                        .postDelayed(
                            {
                                videosAdapter.setLoadingProgressBarVisibility(true)
                                videosViewModel.nextPage = page
                                videosViewModel.getVideos()

                            }, 500
                        )
                }

            })
            setHasFixedSize(true)
        }
    }

    private fun subscribeToLiveData() {
        videosViewModel.videos.observe(viewLifecycleOwner, {
            Timber.i("moviesList: ${videosViewModel.videosList.size}")
            videosAdapter.submitList(it)

            if ((requireContext().applicationContext as MoviesApp).isLargeScreen)
                if (!it.isNullOrEmpty() && videosViewModel._video.value == null)
                    videosViewModel._video.value = it[0]

        })
    }

    private fun subscribeToFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            videosViewModel.videoEvent.collect {
                when (it) {
                    is VideosEvent.NavigateToDetailsScreen ->
                        findNavController().navigate(
                            R.id.detailsFragment,
                            Bundle().apply { putParcelable("video", it.video) })
                    is VideosEvent.PassVideoToDetailsScreen ->
                        videosViewModel._video.value = it.video

                }.exhaustive
            }
        }
    }

}