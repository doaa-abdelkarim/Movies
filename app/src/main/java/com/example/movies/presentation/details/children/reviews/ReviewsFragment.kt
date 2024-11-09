package com.example.movies.presentation.details.children.reviews

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.MoviesApp
import com.example.movies.databinding.FragmentReviewsBinding
import com.example.movies.presentation.common.LoaderStateAdapter
import com.example.movies.presentation.details.parent.DetailsViewModel
import com.example.movies.util.constants.AppConstants.Companion.KEY_STATE_IS_MOVIE
import com.example.movies.util.constants.AppConstants.Companion.KEY_STATE_MOVIE_ID
import com.ragabz.core.base.BaseVBFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReviewsFragment : BaseVBFragment<FragmentReviewsBinding, ReviewsViewModel>(
    viewBindingInflater = FragmentReviewsBinding::inflate
) {
    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    override val viewModel: ReviewsViewModel by viewModels()
    private val detailsViewModel: DetailsViewModel by viewModels({ requireParentFragment() })

    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var loaderStateAdapter: LoaderStateAdapter

    override fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        reviewsAdapter = ReviewsAdapter()
            .apply {
                addLoadStateListener { loadStates ->
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
        loaderStateAdapter = LoaderStateAdapter { reviewsAdapter.retry() }
        binding.apply {
            recyclerViewReviewsList.apply {
                adapter = reviewsAdapter.withLoadStateFooter(loaderStateAdapter)
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
                            viewModel.getMovieReviews(
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
                viewModel.reviews
                    .collectLatest {
                        reviewsAdapter.submitData(it)
                    }
            }
        }
    }

    companion object {
        fun newInstance(selectedMovieId: Int, isMovie: Boolean) =
            ReviewsFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_STATE_MOVIE_ID, selectedMovieId)
                    putBoolean(KEY_STATE_IS_MOVIE, isMovie)
                }
            }
    }
}
