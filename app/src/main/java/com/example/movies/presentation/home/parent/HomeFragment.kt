package com.example.movies.presentation.home.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.example.movies.R
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.domain.entities.Video
import com.example.movies.presentation.MainActivityViewModel
import com.example.movies.presentation.home.children.favorites.FavoritesFragment
import com.example.movies.presentation.home.children.movies.MoviesFragment
import com.example.movies.presentation.home.children.tvshows.TVShowsFragment
import com.example.movies.util.Constants
import com.example.movies.util.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

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

        setFragmentResultListener(Constants.REQUEST_SHOW_FAVORITES) { _, bundle ->
            if (!bundle.getParcelableArrayList<Video>(Constants.RESULT_SHOW_FAVORITES)
                    .isNullOrEmpty()
            )
                showFavoritesFragment()
        }

        subscribeToLiveData()
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

        tabsTitles = resources.getStringArray(R.array.tab_layout_home_titles)

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

    private fun subscribeToLiveData() {
        mainActivityViewModel.favorites.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty() && fragmentList.size < 3)
                showFavoritesFragment()
        }
    }

    private fun showFavoritesFragment() {
        viewPagerAdapter.fragmentList = fragmentList.apply { add(FavoritesFragment()) }
        viewPagerAdapter.notifyDataSetChanged()
        binding.tabLayoutHome.newTab().apply {
            text = tabsTitles[2]
        }
    }

}