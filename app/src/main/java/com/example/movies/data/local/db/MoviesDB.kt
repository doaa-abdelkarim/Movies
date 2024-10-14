package com.example.movies.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.local.db.dao.FavoriteMoviesDao
import com.example.movies.data.local.db.dao.FavoriteTVShowsDao
import com.example.movies.data.local.db.dao.MovieClipsDao
import com.example.movies.data.local.db.dao.MovieReviewsDao
import com.example.movies.data.local.db.dao.MoviesDao
import com.example.movies.data.local.db.dao.TVShowsDao
import com.example.movies.data.local.db.dao.TvShowClipsDao
import com.example.movies.data.local.db.dao.TvShowReviewsDao
import com.example.movies.data.local.models.favorites.LocalFavoriteMovie
import com.example.movies.data.local.models.favorites.LocalFavoriteTVShow
import com.example.movies.data.local.models.videos.movies.LocalMovie
import com.example.movies.data.local.models.videos.movies.LocalMovieClip
import com.example.movies.data.local.models.videos.movies.LocalMovieReview
import com.example.movies.data.local.models.videos.tvshows.LocalTVShow
import com.example.movies.data.local.models.videos.tvshows.LocalTVShowClip
import com.example.movies.data.local.models.videos.tvshows.LocalTVShowReview

@Database(
    entities = [LocalMovie::class, LocalMovieClip::class, LocalMovieReview::class,
        LocalTVShow::class, LocalTVShowClip::class, LocalTVShowReview::class,
        LocalFavoriteMovie::class, LocalFavoriteTVShow::class], version = 1
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