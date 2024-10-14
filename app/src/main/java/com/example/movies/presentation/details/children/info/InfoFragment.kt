package com.example.movies.presentation.details.children.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movies.R
import com.example.movies.databinding.FragmentInfoBinding
import com.example.movies.presentation.details.parent.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


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

        subscribeToLiveData()

    }

    private fun subscribeToLiveData() {
        detailsViewModel.video.observe(viewLifecycleOwner, {
            infoViewModel.getVideoInfo(it)
        })
    }

    companion object {
        fun newInstance() = InfoFragment()
    }
}