package com.example.movies.presentation.details.children.clips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.databinding.FragmentClipsBinding
import com.example.movies.domain.entities.Video
import com.example.movies.util.AppConstants.Companion.KEY_STATE_SELECTED_VIDEO
import com.example.movies.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ClipsFragment : Fragment() {

    private var _binding: FragmentClipsBinding? = null
    private val binding
        get() = _binding!!

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
        observeState()
        listenToEvents()
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

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                clipsViewModel.clips.collect {
                    clipsAdapter.submitList(it)
                }
            }
        }
    }

    private fun listenToEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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
    }

    companion object {
        fun newInstance(selectedVideo: Video?) =
            ClipsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_STATE_SELECTED_VIDEO, selectedVideo)
                }
            }
    }

}