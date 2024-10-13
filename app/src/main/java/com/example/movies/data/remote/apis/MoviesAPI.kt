package com.example.movies.data.remote.apis

import com.example.movies.data.remote.models.tvshow.TVShowsResponse
import com.example.movies.data.remote.models.movie.MovieClipsResponse
import com.example.movies.data.remote.models.movie.MovieDetailsResponse
import com.example.movies.data.remote.models.movie.MovieReviewsResponse
import com.example.movies.data.remote.models.movie.MoviesResponse
import com.example.movies.data.remote.models.tvshow.TVShowClipsResponse
import com.example.movies.data.remote.models.tvshow.TVShowDetailsResponse
import com.example.movies.data.remote.models.tvshow.TVShowReviewsResponse

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/popular")
    suspend fun getMovies(@Query("page") page: Int): MoviesResponse

    @GET("tv/popular")
    suspend fun getTVShows(@Query("page") page: Int): TVShowsResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetailsResponse

    @GET("tv/{tv_id}")
    suspend fun getTVShowDetails(@Path("tv_id") tvShowId: Int): TVShowDetailsResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieClips(@Path("movie_id") movieId: Int): MovieClipsResponse

    @GET("tv/{tv_id}/videos")
    suspend fun getTVShowClips(@Path("tv_id") tvShowId: Int): TVShowClipsResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int): MovieReviewsResponse

    @GET("tv/{tv_id}/reviews")
    suspend fun getTVShowReviews(
        @Path("tv_id") tvShowId: Int,
        @Query("page") page: Int
    ): TVShowReviewsResponse

}