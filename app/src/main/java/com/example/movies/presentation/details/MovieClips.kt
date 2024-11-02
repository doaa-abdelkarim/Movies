package com.example.movies.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.R
import com.example.movies.domain.entities.Clip
import com.example.movies.presentation.common.CustomSubcomposeAsyncImage
import com.example.movies.presentation.details.children.clips.ClipsEvent
import com.example.movies.presentation.details.children.clips.ClipsViewModel
import com.example.movies.ui.theme.darkerGray
import com.example.movies.ui.theme.gray700
import com.example.movies.ui.theme.normalSize16White
import com.example.movies.util.exhaustive

@Composable
fun MoviesClips(
    clipsViewModel: ClipsViewModel = hiltViewModel(),
    navigateToMoviePlayerScreen: (String) -> Unit
) {
    val clips = clipsViewModel.clips.collectAsState().value
    LaunchedEffect(true) {
        clipsViewModel.clipsEvent.collect {
            when (it) {
                is ClipsEvent.EventNavigateToVideoPlayerScreen -> {
                    /*                    if ((appContext as MoviesApp).isLargeScreen)
                                            if (findNavController().currentDestination?.id == R.id.moviesFragment)
                                                findNavController().navigate(
                                                    MoviesFragmentDirections.actionMoviesFragmentToVideoPlayerFragment(
                                                        it.clipKey
                                                    )
                                                )
                                            else
                                                findNavController().navigate(
                                                    TVShowsFragmentDirections.actionTvShowsFragmentToVideoPlayerFragment(
                                                        it.clipKey
                                                    )
                                                )
                                        else*/
                    navigateToMoviePlayerScreen(it.clipKey ?: "")
                }
            }.exhaustive
        }
    }
    ListClips(
        clips = clips,
        onItemClick = { clip -> clipsViewModel.onClipClicked(clip) }
    )
}

@Composable
fun ListClips(
    clips: List<Clip>,
    onItemClick: (Clip) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.background(color = darkerGray),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
    ) {
        itemsIndexed(clips) { index, clip ->
            CellClip(
                clip = clip,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun CellClip(
    clip: Clip,
    onItemClick: (Clip) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = gray700)
            .padding(all = dimensionResource(R.dimen.spacing_normal))
            .clickable {
                onItemClick(clip)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomSubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth(0.35f)
                .aspectRatio(4 / 3f),
            data = clip.clipUri,
            contentDescription = stringResource(R.string.clip_image)
        )
        Text(
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.spacing_normal),
                end = dimensionResource(R.dimen.spacing_normal)
            ),
            text = clip.name ?: "-",
            style = normalSize16White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun CellClipPreview() {
    CellClip(
        clip = Clip(
            videoId = 1,
            clipId = "1",
            name = "Clip name"
        ),
        onItemClick = {}
    )
}