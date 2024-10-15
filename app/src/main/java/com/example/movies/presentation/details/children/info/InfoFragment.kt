package com.example.movies.presentation.details.children.info

import android.content.pm.ConfigurationInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movies.R
import com.example.movies.databinding.FragmentInfoBinding
import com.example.movies.presentation.details.parent.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var binding: FragmentInfoBinding

    private val detailsViewModel: DetailsViewModel by viewModels({ requireParentFragment() })
    private val infoViewModel: InfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentInfoBinding.bind(view)
        binding.infoViewModel = infoViewModel
        binding.lifecycleOwner = this

        observeState()

    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.selectedVideo.collect {
                    Log.d("pppppppppppppppppppp here", "observeState: ")
                    infoViewModel.getVideoInfo(it)
                }
            }
        }
    }

    companion object {
        fun newInstance() = InfoFragment()
    }
}