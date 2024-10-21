package com.example.movies.presentation.details.children.reviews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.MoviesApp
import com.example.movies.databinding.FragmentReviewsBinding
import com.example.movies.domain.entities.Video
import com.example.movies.presentation.common.LoaderStateAdapter
import com.example.movies.presentation.details.parent.DetailsViewModel
import com.example.movies.util.AppConstants.Companion.KEY_STATE_SELECTED_VIDEO
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

        initRecyclerView()
        observeState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecyclerView() {
        reviewsAdapter = ReviewsAdapter()
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
                    detailsViewModel.observableSelectedVideo.collect {
                        it?.let {
                            reviewsViewModel.getVideoReviews(
                                selectedVideo = it,
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

    companion object {
        fun newInstance(selectedVideo: Video?) =
            ReviewsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_STATE_SELECTED_VIDEO, selectedVideo)
                }
            }
    }
}
