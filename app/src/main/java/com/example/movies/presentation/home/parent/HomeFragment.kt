package com.example.movies.presentation.home.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movies.R
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.MainActivityViewModel
import com.example.movies.presentation.home.children.favorites.FavoritesFragment
import com.example.movies.presentation.home.children.movies.MoviesFragment
import com.example.movies.presentation.home.children.tvshows.TVShowsFragment
import com.example.movies.util.constants.AppConstants.Companion.REQUEST_SHOW_FAVORITES
import com.example.movies.util.constants.AppConstants.Companion.RESULT_SHOW_FAVORITES
import com.example.movies.presentation.common.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var tabsTitles: Array<String>
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var fragmentList: ArrayList<Fragment>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initHomeViewPager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(REQUEST_SHOW_FAVORITES) { _, bundle ->
            if (!bundle.getParcelableArrayList<Movie>(RESULT_SHOW_FAVORITES)
                    .isNullOrEmpty()
            )
                addFavoritesFragmentToViewPager()
        }

        observeState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initHomeViewPager() {
        fragmentList = arrayListOf(
            MoviesFragment.newInstance(),
            TVShowsFragment.newInstance()
        )

        tabsTitles = resources.getStringArray(R.array.tab_home_titles)

        viewPagerAdapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        binding.viewPagerHome.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayoutHome, binding.viewPagerHome) { tab, position ->
            tab.text = tabsTitles[position]
        }.attach()

    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainActivityViewModel.favorites.collect {
                    if (it.isNotEmpty() && fragmentList.size < 3)
                        addFavoritesFragmentToViewPager()
                }
            }
        }
    }

    private fun addFavoritesFragmentToViewPager() {
        viewPagerAdapter.fragmentList = fragmentList.apply { add(FavoritesFragment()) }
        viewPagerAdapter.notifyDataSetChanged()
        binding.tabLayoutHome.newTab().apply {
            text = tabsTitles[2]
        }
    }

}