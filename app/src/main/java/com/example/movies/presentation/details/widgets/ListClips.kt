package com.example.movies.presentation.details.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.movies.R
import com.example.movies.domain.entities.Clip
import com.example.movies.ui.theme.darkerGray

@Composable
fun ListClips(
    clips: List<Clip>,
    onItemClick: (Clip) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = darkerGray),
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