package com.example.movies.domain.repositories

import androidx.paging.PagingData
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface BaseMoviesRepository {
    fun getMovies(): Flow<PagingData<Movie>>
    fun getTVShows(): Flow<PagingData<Movie>>
    suspend fun getMovieDetails(id: Int): Movie
    suspend fun getTVShowDetails(id: Int): Movie
    suspend fun getMovieClips(id: Int): List<Clip>
    suspend fun getTVShowClips(id: Int): List<Clip>
    fun getMovieReviews(id: Int): Flow<PagingData<Review>>
    fun getTVShowReviews(id: Int): Flow<PagingData<Review>>
}