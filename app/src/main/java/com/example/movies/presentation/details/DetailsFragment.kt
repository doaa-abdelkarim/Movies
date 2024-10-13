package com.example.movies.presentation.details

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.movies.MoviesApp
import com.example.movies.R
import com.example.movies.databinding.FragmentDetailsBinding
import com.example.movies.presentation.MainActivityViewModel
import com.example.movies.presentation.details.clips.ClipsFragment
import com.example.movies.presentation.details.info.InfoFragment
import com.example.movies.presentation.details.reviews.ReviewsFragment
import com.example.movies.presentation.home.base.VideosViewModel
import com.example.movies.presentation.home.movies.MoviesFragment
import com.example.movies.presentation.home.movies.MoviesViewModel
import com.example.movies.presentation.home.tvshows.TVShowsViewModel
import com.example.movies.util.Constants.Companion.REQUEST_SHOW_FAVORITES
import com.example.movies.util.Constants.Companion.RESULT_SHOW_FAVORITES
import com.example.movies.util.ViewPagerAdapter
import com.example.movies.util.exhaustive
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var videosViewModel: VideosViewModel
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private val detailsViewModel: DetailsViewModel by viewModels()

    private lateinit var binding: FragmentDetailsBinding

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if ((appContext as MoviesApp).isLargeScreen)
            if (parentFragment?.javaClass == MoviesFragment::class.java)
                videosViewModel =
                    ViewModelProvider(requireParentFragment()).get(MoviesViewModel::class.java)
            else
                videosViewModel =
                    ViewModelProvider(requireParentFragment()).get(TVShowsViewModel::class.java)
        else
            detailsViewModel.updateVideoLiveData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view)
        binding.lifecycleOwner = this

        initViews()
        initDetailsViewPager()
        subscribeToLiveData()
        subscribeToFlow()
    }

    private fun initViews() {
        binding.buttonAddToFavorites.setOnClickListener {
            detailsViewModel.onAddFavorite()
        }
    }

    private fun initDetailsViewPager() {
        val fragmentList = arrayListOf(
            InfoFragment.newInstance(),
            ClipsFragment.newInstance(),
            ReviewsFragment.newInstance()
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

    private fun subscribeToLiveData() {
        if (::videosViewModel.isInitialized) {
            videosViewModel.video.observe(viewLifecycleOwner, {
                detailsViewModel.videoArg = it
                detailsViewModel.updateVideoLiveData()
                binding.video = it
            })
            detailsViewModel.favorites.observe(viewLifecycleOwner, {
                mainActivityViewModel.favorites.value = it
            })
        } else {
            detailsViewModel.video.observe(viewLifecycleOwner, {
                binding.video = it
            })
            detailsViewModel.favorites.observe(viewLifecycleOwner, {
                setFragmentResult(REQUEST_SHOW_FAVORITES, bundleOf(RESULT_SHOW_FAVORITES to it))
            })
        }
    }

    private fun subscribeToFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            detailsViewModel.detailsEvent.collect {
                when (it) {
                    is DetailsViewModel.DetailsEvent.ShowSavedMessage ->
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }.exhaustive
            }
        }
    }

    companion object {
        fun newInstance() = DetailsFragment()
    }

}

