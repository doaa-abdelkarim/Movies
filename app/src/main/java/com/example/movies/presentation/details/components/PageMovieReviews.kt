package com.example.movies.presentation.details.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.presentation.details.draft.children.reviews.ReviewsViewModel

@Composable
fun PageMoviesReviews(
    reviewsViewModel: ReviewsViewModel = hiltViewModel()
) {
    val reviews = reviewsViewModel.reviews.collectAsLazyPagingItems()
    ListReviews(reviews = reviews)
}