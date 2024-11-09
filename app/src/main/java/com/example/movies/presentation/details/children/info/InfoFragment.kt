package com.example.movies.presentation.details.children.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movies.R
import com.example.movies.databinding.FragmentInfoBinding
import com.example.movies.presentation.details.parent.DetailsViewModel
import com.example.movies.presentation.home.UiState
import com.ragabz.core.base.BaseDBFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class InfoFragment : BaseDBFragment<FragmentInfoBinding, ViewModel>(
    layoutId = R.layout.fragment_info
) {

    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")
    private val detailsViewModel: DetailsViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
    }

    override fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.movie.collect { uiState ->
                    if (uiState is UiState.Data)
                        binding.movie = uiState.data
                }
            }
        }
    }

    companion object {
        fun newInstance() = InfoFragment()
    }
}