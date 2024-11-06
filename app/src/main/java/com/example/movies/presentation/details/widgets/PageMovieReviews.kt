package com.example.movies.presentation.details.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.presentation.details.draft.children.reviews.ReviewsViewModel
import com.example.movies.presentation.details.draft.parent.DetailsViewModel

@Composable
fun PageMoviesReviews(
    reviewsViewModel: ReviewsViewModel = hiltViewModel(),
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    ) {
    val reviews = reviewsViewModel.reviews.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        detailsViewModel.observedMovie.collect {
            it?.let {
                reviewsViewModel.getMovieReviews(
                    observedMovie = it,
                    isLargeScreen = true
                )
            }
        }
    }
    ListReviews(reviews = reviews)
}