package com.example.movies.presentation.details.reviews

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.FragmentReviewsListBinding
import com.example.movies.presentation.details.DetailsViewModel
import com.example.movies.util.Constants.Companion.PAGE
import com.example.movies.util.EndlessRecyclerViewScrollListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ReviewsFragment : Fragment() {

    private var _binding: FragmentReviewsListBinding? = null
    private val binding get() = _binding!!

    private val detailsViewModel: DetailsViewModel by viewModels({ requireParentFragment() })
    private val reviewsViewModel: ReviewsViewModel by viewModels()

    private lateinit var reviewsAdapter: ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //CodeReview you can initialize your adapter when creating it, there's no need to assign it in onCreate
        initReviewsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribeToLiveData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initReviewsAdapter() {
        reviewsAdapter = ReviewsAdapter()
    }

    private fun initRecyclerView() {
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
                                    reviewsViewModel.getVideoReviews(
                                        detailsViewModel.video.value
                                    )

                                },
                                500
                            )
                    }

                })
                setHasFixedSize(true)
            }
        }
    }

    private fun subscribeToLiveData() {
        detailsViewModel.video.observe(viewLifecycleOwner, {
            reviewsViewModel.nextPage = PAGE
            reviewsViewModel.reviewsList.clear()
            reviewsViewModel.getVideoReviews(it)
        })

        reviewsViewModel.reviews.observe(viewLifecycleOwner, {
            Timber.d(it.toString())
            reviewsAdapter.submitList(it)
            reviewsAdapter.notifyDataSetChanged()
        })
    }

    companion object {
        fun newInstance() = ReviewsFragment()
    }
}
