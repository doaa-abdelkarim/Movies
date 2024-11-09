package com.example.movies.presentation.home.children.favorites

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.databinding.FragmentFavoritesBinding
import com.example.movies.presentation.MainActivityViewModel
import com.ragabz.core.base.BaseVBFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BaseVBFragment<FragmentFavoritesBinding, ViewModel>(
    viewBindingInflater = FragmentFavoritesBinding::inflate
) {

    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun initViews() {
        super.initViews()

        initRecyclerView()
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

    override fun observeState() {
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