package com.example.movies.presentation.details.parent

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movies.MoviesApp
import com.example.movies.R
import com.example.movies.databinding.FragmentDetailsBinding
import com.example.movies.domain.entities.Video
import com.example.movies.presentation.MainActivityViewModel
import com.example.movies.presentation.details.children.clips.ClipsFragment
import com.example.movies.presentation.details.children.info.InfoFragment
import com.example.movies.presentation.details.children.reviews.ReviewsFragment
import com.example.movies.presentation.home.base.VideosViewModel
import com.example.movies.presentation.home.children.movies.MoviesFragment
import com.example.movies.presentation.home.children.movies.MoviesViewModel
import com.example.movies.presentation.home.children.tvshows.TVShowsViewModel
import com.example.movies.util.AppConstants.Companion.KEY_STATE_SELECTED_VIDEO
import com.example.movies.util.AppConstants.Companion.REQUEST_SHOW_FAVORITES
import com.example.movies.util.AppConstants.Companion.RESULT_SHOW_FAVORITES
import com.example.movies.util.ViewPagerAdapter
import com.example.movies.util.exhaustive
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private lateinit var videosViewModel: VideosViewModel
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private val detailsViewModel: DetailsViewModel by viewModels()

    private lateinit var binding: FragmentDetailsBinding
    private var selectedVideo: Video? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedVideo = arguments?.getParcelable(KEY_STATE_SELECTED_VIDEO)

        if ((appContext as MoviesApp).isLargeScreen)
            videosViewModel = if (parentFragment?.javaClass == MoviesFragment::class.java)
                ViewModelProvider(requireParentFragment())[MoviesViewModel::class.java]
            else
                ViewModelProvider(requireParentFragment())[TVShowsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view)
        binding.lifecycleOwner = this

        initViews()
        initDetailsViewPager()
        observeState()
        listenToEvents()
    }

    private fun initViews() {
        binding.buttonAddToFavorites.setOnClickListener {
            detailsViewModel.onAddToFavorite(
                isLargeScreen = (appContext as MoviesApp).isLargeScreen
            )
        }
    }

    private fun initDetailsViewPager() {
        val fragmentList = arrayListOf(
            InfoFragment.newInstance(selectedVideo),
            ClipsFragment.newInstance(selectedVideo),
            ReviewsFragment.newInstance(selectedVideo)
        )

        val tabsTitles = resources.getStringArray(R.array.tab_layout_details_titles)

        val viewPagerAdapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        binding.viewPagerDetails.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayoutDetails, binding.viewPagerDetails) { tab, position ->
            tab.text = tabsTitles[position]
        }.attach()

    }


    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.favorites.collect {
                    mainActivityViewModel.favorites.value = it
                }
            }
        }

        if ((appContext as MoviesApp).isLargeScreen) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    videosViewModel.selectedVideo.collect {
                        detailsViewModel.updateObservableSelectedVideo(selectedVideo = it)
                        binding.video = it
                    }
                }
            }
        } else {
            binding.video = selectedVideo
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    detailsViewModel.favorites.collect {
                        setFragmentResult(
                            REQUEST_SHOW_FAVORITES,
                            bundleOf(RESULT_SHOW_FAVORITES to it)
                        )
                    }
                }
            }
        }
    }

    private fun listenToEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.detailsEvent.collect {
                    when (it) {
                        is DetailsViewModel.DetailsEvent.ShowSavedMessage ->
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }.exhaustive
                }
            }
        }
    }

    companion object {
        fun newInstance() = DetailsFragment()
    }

}

