package com.example.movies.presentation.details.parent

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.movies.MoviesApp
import com.example.movies.R
import com.example.movies.databinding.FragmentDetailsBinding
import com.example.movies.presentation.MainActivityViewModel
import com.example.movies.presentation.common.ViewPagerAdapter
import com.example.movies.presentation.details.children.clips.ClipsFragment
import com.example.movies.presentation.details.children.info.InfoFragment
import com.example.movies.presentation.details.children.reviews.ReviewsFragment
import com.example.movies.presentation.home.UiState
import com.example.movies.presentation.home.base.BaseVideosViewModel
import com.example.movies.presentation.home.children.movies.MoviesFragment
import com.example.movies.presentation.home.children.movies.MoviesViewModel
import com.example.movies.presentation.home.children.tvshows.TVShowsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.ragabz.core.base.BaseDBFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : BaseDBFragment<FragmentDetailsBinding, DetailsViewModel>(
    layoutId = R.layout.fragment_details
) {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    override val viewModel: DetailsViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var baseVideosViewModel: BaseVideosViewModel

    private val args: DetailsFragmentArgs by navArgs()
    private var selectedMovieId: Int = -1
    private var isMovie: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if ((appContext as MoviesApp).isLargeScreen)
            baseVideosViewModel = if (parentFragment?.javaClass == MoviesFragment::class.java)
                ViewModelProvider(requireParentFragment())[MoviesViewModel::class.java]
            else
                ViewModelProvider(requireParentFragment())[TVShowsViewModel::class.java]
        else {
            selectedMovieId = args.movieId
            isMovie = args.isMovie
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
    }

    override fun initViews() {
        initDetailsViewPager()
    }

    private fun initDetailsViewPager() {
        val fragmentList = arrayListOf(
            InfoFragment.newInstance(),
            ClipsFragment.newInstance(selectedMovieId = selectedMovieId, isMovie = isMovie),
            ReviewsFragment.newInstance(selectedMovieId = selectedMovieId, isMovie = isMovie)
        )

        val tabsTitles = resources.getStringArray(R.array.tabs_details_titles)

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

    override fun attachListeners() {
        binding.buttonAddToFavorites.setOnClickListener {
            viewModel.movie.value.let {
                if (it is UiState.Data)
                    mainActivityViewModel.onAddToFavoriteClick(movie = it.data)
            }
        }
    }

    override fun observeState() {
        if ((appContext as MoviesApp).isLargeScreen) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    baseVideosViewModel.observedVideo.collect {
                        viewModel.updateObservedMovie(movie = it)
                        it?.let {
                            viewModel.getMovieDetails(
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
                viewModel.movie.collect { uiState ->
                    when (uiState) {
                        is UiState.Initial -> {}

                        is UiState.Loading -> onInProgress()

                        is UiState.Data -> {
                            onRequestSucceeded()
                            binding.movie = uiState.data
                        }

                        is UiState.Error -> onRequestFailed(error = uiState.error)
                    }
                }
            }
        }
    }

    override fun onRequestSucceeded() {
        super.onRequestSucceeded()
        binding.buttonAddToFavorites.isEnabled = true
    }

    override fun onRequestFailed(error: Throwable) {
        super.onRequestFailed(error = error)
        binding.buttonAddToFavorites.isEnabled = false
    }

    companion object {
        fun newInstance() = DetailsFragment()
    }

}

