package com.example.movies.presentation.details.draft.parent

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import com.example.movies.presentation.details.draft.children.clips.ClipsFragment
import com.example.movies.presentation.details.draft.children.info.InfoFragment
import com.example.movies.presentation.details.draft.children.reviews.ReviewsFragment
import com.example.movies.presentation.home.draft.base.VideosViewModel
import com.example.movies.presentation.home.draft.children.movies.MoviesFragment
import com.example.movies.presentation.home.draft.children.movies.MoviesViewModel
import com.example.movies.presentation.home.draft.children.tvshows.TVShowsViewModel
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

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var videosViewModel: VideosViewModel
    private val detailsViewModel: DetailsViewModel by viewModels()

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private var selectedMovieId: Int = -1
    private var isMovie: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if ((appContext as MoviesApp).isLargeScreen)
            videosViewModel = if (parentFragment?.javaClass == MoviesFragment::class.java)
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

        binding = FragmentDetailsBinding.bind(view)
        binding.lifecycleOwner = this

        initViews()
        initDetailsViewPager()
        observeState()
    }

    private fun initViews() {
        binding.buttonAddToFavorites.setOnClickListener {
            detailsViewModel.movie.value?.let {
                mainActivityViewModel.onAddToFavoriteClick(movie = it)
            }
        }
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


    private fun observeState() {
        if ((appContext as MoviesApp).isLargeScreen) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    videosViewModel.observedVideo.collect {
                        detailsViewModel.updateObservedMovie(movie = it)
                        it?.let {
                            detailsViewModel.getMovieDetails(
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
                detailsViewModel.movie.collect {
                    binding.movie = it
                }
            }
        }
    }

    companion object {
        fun newInstance() = DetailsFragment()
    }

}

