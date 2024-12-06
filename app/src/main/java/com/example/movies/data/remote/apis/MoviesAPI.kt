package com.example.movies.data.remote.apis

import com.example.movies.data.remote.models.RemoteClips
import com.example.movies.data.remote.models.RemoteMovie
import com.example.movies.data.remote.models.RemoteMovies
import com.example.movies.data.remote.models.RemoteReviews
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/popular")
    suspend fun getMovies(@Query("page") page: Int): RemoteMovies

    @GET("tv/popular")
    suspend fun getTVShows(@Query("page") page: Int): RemoteMovies

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): RemoteMovie

    @GET("tv/{tv_id}")
    suspend fun getTVShowDetails(@Path("tv_id") tvShowId: Int): RemoteMovie

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieClips(@Path("movie_id") movieId: Int): RemoteClips

    @GET("tv/{tv_id}/videos")
    suspend fun getTVShowClips(@Path("tv_id") tvShowId: Int): RemoteClips

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): RemoteReviews

    @GET("tv/{tv_id}/reviews")
    suspend fun getTVShowReviews(
        @Path("tv_id") tvShowId: Int,
        @Query("page") page: Int
    ): RemoteReviews

}