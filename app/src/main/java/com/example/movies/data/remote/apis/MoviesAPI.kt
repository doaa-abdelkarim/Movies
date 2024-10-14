package com.example.movies.data.remote.apis

import com.example.movies.data.remote.models.tvshow.RemoteTVShows
import com.example.movies.data.remote.models.movies.RemoteMovieClips
import com.example.movies.data.remote.models.movies.RemoteMovieDetails
import com.example.movies.data.remote.models.movies.RemoteMovieReviews
import com.example.movies.data.remote.models.movies.RemoteMovies
import com.example.movies.data.remote.models.tvshow.RemoteTVShowClips
import com.example.movies.data.remote.models.tvshow.RemoteTVShowDetails
import com.example.movies.data.remote.models.tvshow.RemoteTVShowReviews

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/popular")
    suspend fun getMovies(@Query("page") page: Int): RemoteMovies

    @GET("tv/popular")
    suspend fun getTVShows(@Query("page") page: Int): RemoteTVShows

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): RemoteMovieDetails

    @GET("tv/{tv_id}")
    suspend fun getTVShowDetails(@Path("tv_id") tvShowId: Int): RemoteTVShowDetails

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieClips(@Path("movie_id") movieId: Int): RemoteMovieClips

    @GET("tv/{tv_id}/videos")
    suspend fun getTVShowClips(@Path("tv_id") tvShowId: Int): RemoteTVShowClips

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int): RemoteMovieReviews

    @GET("tv/{tv_id}/reviews")
    suspend fun getTVShowReviews(
        @Path("tv_id") tvShowId: Int,
        @Query("page") page: Int
    ): RemoteTVShowReviews

}