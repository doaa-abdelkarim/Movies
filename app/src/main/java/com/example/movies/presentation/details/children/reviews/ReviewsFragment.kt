package com.example.movies.presentation.details.children.reviews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.MoviesApp
import com.example.movies.R
import com.example.movies.databinding.FragmentReviewsBinding
import com.example.movies.presentation.common.LoaderStateAdapter
import com.example.movies.presentation.details.parent.DetailsViewModel
import com.example.movies.util.constants.AppConstants.Companion.KEY_STATE_IS_MOVIE
import com.example.movies.util.constants.AppConstants.Companion.KEY_STATE_MOVIE_ID
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReviewsFragment : Fragment() {
    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private var _binding: FragmentReviewsBinding? = null
    private val binding
        get() = _binding!!

    private val detailsViewModel: DetailsViewModel by viewModels({ requireParentFragment() })
    private val reviewsViewModel: ReviewsViewModel by viewModels()

    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var loaderStateAdapter: LoaderStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initViews() {
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

    private fun observeState() {
        if ((appContext as MoviesApp).isLargeScreen) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    detailsViewModel.observedMovie.collect {
                        it?.let {
                            reviewsViewModel.getMovieReviews(
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
                reviewsViewModel.reviews
                    .collectLatest {
                        reviewsAdapter.submitData(it)
                    }
            }
        }
    }

    private fun onInProgress() {
        binding.loader.constraintLayoutLoaderRoot.visibility = View.VISIBLE
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
        binding.loader.constraintLayoutLoaderRoot.visibility = View.GONE
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
