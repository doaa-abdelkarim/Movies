package com.example.movies.presentation.details.children.reviews

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.FragmentReviewsBinding
import com.example.movies.domain.entities.Video
import com.example.movies.presentation.details.parent.DetailsViewModel
import com.example.movies.util.AppConstants.Companion.KEY_STATE_SELECTED_VIDEO
import com.example.movies.util.EndlessRecyclerViewScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ReviewsFragment : Fragment() {

    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!

    private val detailsViewModel: DetailsViewModel by viewModels({ requireParentFragment() })
    private val reviewsViewModel: ReviewsViewModel by viewModels()

    private lateinit var reviewsAdapter: ReviewsAdapter

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
        binding.apply {
            recyclerViewReviewsList.apply {
                adapter = reviewsAdapter
                val layoutManager = LinearLayoutManager(requireContext())
                this.layoutManager = layoutManager
                addOnScrollListener(object :
                    EndlessRecyclerViewScrollListener(layoutManager, reviewsViewModel.nextPage) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                        Handler(Looper.getMainLooper())
                            .postDelayed(
                                {
                                    reviewsAdapter.setLoadingProgressBarVisibility(true)
                                    reviewsViewModel.nextPage = page
                                    reviewsViewModel.getVideoReviews()
                                },
                                500
                            )
                    }
                })
                setHasFixedSize(true)
            }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                reviewsViewModel.reviews.collect {
                    Timber.d(it.toString())
                    reviewsAdapter.submitList(it)
                    reviewsAdapter.notifyDataSetChanged()
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
