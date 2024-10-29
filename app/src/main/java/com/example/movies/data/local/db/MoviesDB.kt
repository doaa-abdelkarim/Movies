package com.example.movies.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.local.db.dao.ClipsDao
import com.example.movies.data.local.db.dao.FavoritesDao
import com.example.movies.data.local.db.dao.ReviewsDao
import com.example.movies.data.local.db.dao.MovieReviewsRemoteKeysDao
import com.example.movies.data.local.db.dao.MoviesDao
import com.example.movies.data.local.db.dao.MoviesRemoteKeysDao
import com.example.movies.data.local.db.dao.TVShowReviewsRemoteKeysDao
import com.example.movies.data.local.db.dao.TVShowsRemoteKeysDao
import com.example.movies.data.local.models.LocalFavorite
import com.example.movies.data.local.models.remotekeys.MovieReviewsRemoteKeys
import com.example.movies.data.local.models.remotekeys.MoviesRemoteKeys
import com.example.movies.data.local.models.remotekeys.TVShowReviewsRemoteKeys
import com.example.movies.data.local.models.remotekeys.TVShowsRemoteKeys
import com.example.movies.data.local.models.LocalClip
import com.example.movies.data.local.models.LocalMovie
import com.example.movies.data.local.models.LocalReview

@Database(
    entities = [
        LocalMovie::class, LocalClip::class, LocalReview::class,
        LocalFavorite::class,
        MoviesRemoteKeys::class, TVShowsRemoteKeys::class,
        MovieReviewsRemoteKeys::class, TVShowReviewsRemoteKeys::class
    ], version = 1
)
abstract class MoviesDB : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun clipsDao(): ClipsDao
    abstract fun reviewsDao(): ReviewsDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun moviesRemoteKeysDao(): MoviesRemoteKeysDao
    abstract fun tvShowsRemoteKeysDao(): TVShowsRemoteKeysDao
    abstract fun movieReviewsRemoteKeysDao(): MovieReviewsRemoteKeysDao
    abstract fun tvShowReviewsRemoteKeysDao(): TVShowReviewsRemoteKeysDao

}