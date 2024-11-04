package com.example.movies.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.paging.compose.LazyPagingItems
import com.example.movies.R
import com.example.movies.domain.entities.Review
import com.example.movies.ui.theme.darkerGray

@Composable
fun ListReviews(reviews: LazyPagingItems<Review>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = darkerGray),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_normal))
    ) {
        items(reviews.itemCount) { index ->
            reviews[index]?.let { CellReview(review = it) }
        }
    }
}