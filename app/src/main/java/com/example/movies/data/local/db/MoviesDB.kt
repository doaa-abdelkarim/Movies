package com.example.movies.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.local.db.dao.FavoritesDao
import com.example.movies.data.local.db.dao.MovieClipsDao
import com.example.movies.data.local.db.dao.MovieReviewsDao
import com.example.movies.data.local.db.dao.MovieReviewsRemoteKeysDao
import com.example.movies.data.local.db.dao.MoviesDao
import com.example.movies.data.local.db.dao.MoviesRemoteKeysDao
import com.example.movies.data.local.db.dao.TVShowReviewsDao
import com.example.movies.data.local.db.dao.TVShowReviewsRemoteKeysDao
import com.example.movies.data.local.db.dao.TVShowsDao
import com.example.movies.data.local.db.dao.TVShowsRemoteKeysDao
import com.example.movies.data.local.db.dao.TvShowClipsDao
import com.example.movies.data.local.models.favorites.LocalFavorite
import com.example.movies.data.local.models.remotekeys.MovieReviewsRemoteKeys
import com.example.movies.data.local.models.remotekeys.MoviesRemoteKeys
import com.example.movies.data.local.models.remotekeys.TVShowReviewsRemoteKeys
import com.example.movies.data.local.models.remotekeys.TVShowsRemoteKeys
import com.example.movies.data.local.models.videos.movies.LocalMovie
import com.example.movies.data.local.models.videos.movies.LocalMovieClip
import com.example.movies.data.local.models.videos.movies.LocalMovieReview
import com.example.movies.data.local.models.videos.tvshows.LocalTVShow
import com.example.movies.data.local.models.videos.tvshows.LocalTVShowClip
import com.example.movies.data.local.models.videos.tvshows.LocalTVShowReview

@Database(
    entities = [
        LocalMovie::class, LocalMovieClip::class, LocalMovieReview::class,
        LocalTVShow::class, LocalTVShowClip::class, LocalTVShowReview::class,
        LocalFavorite::class,
        MoviesRemoteKeys::class, TVShowsRemoteKeys::class,
        MovieReviewsRemoteKeys::class, TVShowReviewsRemoteKeys::class
    ], version = 1
)
abstract class MoviesDB : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun movieClipsDao(): MovieClipsDao
    abstract fun movieReviewsDao(): MovieReviewsDao
    abstract fun tvShowsDao(): TVShowsDao
    abstract fun tvShowClipsDao(): TvShowClipsDao
    abstract fun tvShowReviewsDao(): TVShowReviewsDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun moviesRemoteKeysDao(): MoviesRemoteKeysDao
    abstract fun tvShowsRemoteKeysDao(): TVShowsRemoteKeysDao
    abstract fun movieReviewsRemoteKeysDao(): MovieReviewsRemoteKeysDao
    abstract fun tvShowReviewsRemoteKeysDao(): TVShowReviewsRemoteKeysDao

}