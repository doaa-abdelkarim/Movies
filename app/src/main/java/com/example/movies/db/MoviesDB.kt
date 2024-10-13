package com.example.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.local.models.favorites.DatabaseFavoriteMovie
import com.example.movies.data.local.models.favorites.DatabaseFavoriteTVShow
import com.example.movies.data.local.models.videos.movie.DatabaseMovie
import com.example.movies.data.local.models.videos.movie.DatabaseMovieClip
import com.example.movies.data.local.models.videos.movie.DatabaseMovieReview
import com.example.movies.data.local.models.videos.tvshow.DatabaseTVShow
import com.example.movies.data.local.models.videos.tvshow.DatabaseTVShowClip
import com.example.movies.data.local.models.videos.tvshow.DatabaseTVShowReview
import com.example.movies.db.dao.*

@Database(
    entities = [DatabaseMovie::class, DatabaseMovieClip::class, DatabaseMovieReview::class,
        DatabaseTVShow::class, DatabaseTVShowClip::class, DatabaseTVShowReview::class,
        DatabaseFavoriteMovie::class, DatabaseFavoriteTVShow::class], version = 1
)
abstract class MoviesDB : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun movieClipsDao(): MovieClipsDao
    abstract fun movieReviewsDao(): MovieReviewsDao
    abstract fun tvShowsDao(): TVShowsDao
    abstract fun tvShowClipsDao(): TvShowClipsDao
    abstract fun tvShowReviewsDao(): TvShowReviewsDao
    abstract fun favoriteMoviesDao(): FavoriteMoviesDao
    abstract fun favoriteTVShowsDao(): FavoriteTVShowsDao

}