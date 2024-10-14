package com.example.movies.presentation.home.children.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.databinding.FragmentFavoritesBinding
import com.example.movies.presentation.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

//CodeReview favorites package should be moved outside details package
@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding!!

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()

    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFavoritesAdapter()
        initRecyclerView()
        subscribeToLiveData()
    }

    override fun onResume() {
        super.onResume()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            favoritesViewModel.favorites.value = mainActivityViewModel.getAllFavorites()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initFavoritesAdapter() {
        favoritesAdapter = FavoritesAdapter()
    }

    private fun initRecyclerView() {
        binding.apply {
            recyclerViewFavoritesList.apply {
                adapter = favoritesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
    }

    private fun subscribeToLiveData() {
        favoritesViewModel.favorites.observe(viewLifecycleOwner) {
            favoritesAdapter.submitList(it)
        }
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }

}