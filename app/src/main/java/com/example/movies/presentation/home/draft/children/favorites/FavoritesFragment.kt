package com.example.movies.presentation.home.draft.children.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.databinding.FragmentFavoritesBinding
import com.example.movies.presentation.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding!!

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

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
        initRecyclerView()
        observeState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecyclerView() {
        favoritesAdapter = FavoritesAdapter()
        binding.apply {
            recyclerViewFavoritesList.apply {
                adapter = favoritesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainActivityViewModel.favorites.collect {
                    favoritesAdapter.submitList(it)
                }
            }
        }
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }

}