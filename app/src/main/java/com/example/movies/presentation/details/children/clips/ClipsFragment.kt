package com.example.movies.presentation.details.children.clips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.databinding.FragmentClipsBinding
import com.example.movies.presentation.details.parent.DetailsViewModel
import com.example.movies.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ClipsFragment : Fragment() {

    private var _binding: FragmentClipsBinding? = null
    private val binding
        get() = _binding!!

    private val detailsViewModel: DetailsViewModel by viewModels({ requireParentFragment() })
    private val clipsViewModel: ClipsViewModel by viewModels()

    private lateinit var clipsAdapter: ClipsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribeToLiveData()
        subscribeToFlow()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecyclerView() {
        clipsAdapter = ClipsAdapter(ClipsAdapter.OnItemClickListener {
            clipsViewModel.onClipClicked(it)
        })
        binding.apply {
            recyclerViewClipsList.apply {
                adapter = clipsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
    }

    private fun subscribeToLiveData() {
        detailsViewModel.video.observe(viewLifecycleOwner) {
            clipsViewModel.getVideoClips(it)
        }

        clipsViewModel.clips.observe(viewLifecycleOwner) {
            clipsAdapter.submitList(it)
        }
    }

    private fun subscribeToFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            clipsViewModel.clipsEvent.collect {
                when (it) {
                    is ClipsEvent.EventNavigateToVideoPlayerScreen -> {

                        findNavController().navigate(
                            R.id.videoPlayerFragment,
                            bundleOf(
                                "clipKey" to it.clipKey,
                                "clipName" to it.clipName
                            )
                        )
                    }
                }.exhaustive
            }
        }
    }

    companion object {
        fun newInstance() = ClipsFragment()
    }

}