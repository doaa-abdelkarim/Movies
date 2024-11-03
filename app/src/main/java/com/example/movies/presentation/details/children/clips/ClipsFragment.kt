package com.example.movies.presentation.details.children.clips

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.MoviesApp
import com.example.movies.R
import com.example.movies.databinding.FragmentClipsBinding
import com.example.movies.presentation.details.parent.DetailsFragmentDirections
import com.example.movies.presentation.details.parent.DetailsViewModel
import com.example.movies.presentation.home.children.movies.MoviesFragmentDirections
import com.example.movies.presentation.home.children.tvshows.TVShowsFragmentDirections
import com.example.movies.util.constants.AppConstants.Companion.KEY_STATE_IS_MOVIE
import com.example.movies.util.constants.AppConstants.Companion.KEY_STATE_MOVIE_ID
import com.example.movies.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ClipsFragment : Fragment() {
    @Inject
    @ApplicationContext
    lateinit var appContext: Context

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
        observeState()
        listenToEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecyclerView() {
        clipsAdapter = ClipsAdapter(ClipsAdapter.OnItemClickListener {
            clipsViewModel.onClipClick(it)
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
        if ((appContext as MoviesApp).isLargeScreen) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    detailsViewModel.observedMovie.collect {
                        it?.let {
                            clipsViewModel.getMovieClips(
                                observedMovie = it,
                                isLargeScreen = true
                            )
                        }
                    }
                }
            }
        }

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
                        is ClipsEvent.EventNavigateToMoviePlayerScreen -> {
                            if ((appContext as MoviesApp).isLargeScreen)
                                if (findNavController().currentDestination?.id == R.id.moviesFragment)
                                    findNavController().navigate(
                                        MoviesFragmentDirections.actionMoviesFragmentToMoviePlayerFragment(
                                            it.clipKey ?: ""
                                        )
                                    )
                                else
                                    findNavController().navigate(
                                        TVShowsFragmentDirections.actionTvShowsFragmentToMoviePlayerFragment(
                                            it.clipKey ?: ""
                                        )
                                    )
                            else
                                findNavController().navigate(
                                    DetailsFragmentDirections.actionDetailsFragmentToMoviePlayerFragment(
                                        it.clipKey ?: ""
                                    )
                                )
                        }
                    }.exhaustive
                }
            }
        }
    }

    companion object {
        fun newInstance(selectedMovieId: Int, isMovie: Boolean) =
            ClipsFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_STATE_MOVIE_ID, selectedMovieId)
                    putBoolean(KEY_STATE_IS_MOVIE, isMovie)
                }
            }
    }

}