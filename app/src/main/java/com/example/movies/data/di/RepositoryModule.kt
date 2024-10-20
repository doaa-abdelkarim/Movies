package com.example.movies.data.di

import android.content.Context
import com.example.movies.data.local.db.MoviesDB
import com.example.movies.data.local.db.dao.FavoriteMoviesDao
import com.example.movies.data.local.db.dao.FavoriteTVShowsDao
import com.example.movies.data.local.db.dao.MovieClipsDao
import com.example.movies.data.local.db.dao.MoviesDao
import com.example.movies.data.local.db.dao.MoviesRemoteKeysDao
import com.example.movies.data.local.db.dao.TVShowsDao
import com.example.movies.data.local.db.dao.TVShowsRemoteKeysDao
import com.example.movies.data.local.db.dao.TvShowClipsDao
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.repositories.FavoriteMoviesRepository
import com.example.movies.data.repositories.FavoriteTVShowsRepository
import com.example.movies.data.repositories.MoviesRepository2
import com.example.movies.data.repositories.TVShowsRepository2
import com.example.movies.domain.repositories.BaseFavoriteRepository
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.NetworkHandler
import com.example.movies.util.NetworkHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Qualifier

@InstallIn(ViewModelComponent::class)
@Module
class RepositoryModule {

    //Network is the single source of truth
  /*  @Provides
    @MoviesRepo
    fun provideMoviesRepository(
        moviesAPI: MoviesAPI,
    ): BaseVideosRepository =
        MoviesRepository1(
            moviesAPI = moviesAPI,
        )

    @Provides
    @TVShowsRepo
    fun provideTVShowsRepository(
        moviesAPI: MoviesAPI,
    ): BaseVideosRepository =
        TVShowsRepository1(
            moviesAPI = moviesAPI,
        )*/

    //Room is the single source of truth
    @Provides
    @MoviesRepo
    fun provideMoviesRepository(
        networkHandler: NetworkHandler,
        moviesAPI: MoviesAPI,
        moviesDB: MoviesDB,
        moviesRemoteKeysDao: MoviesRemoteKeysDao,
        moviesDao: MoviesDao,
        movieClipsDao: MovieClipsDao,
    ): BaseVideosRepository =
        MoviesRepository2(
            networkHandler = networkHandler,
            moviesAPI = moviesAPI,
            moviesDB = moviesDB,
            moviesRemoteKeysDao = moviesRemoteKeysDao,
            moviesDao = moviesDao,
            movieClipsDao = movieClipsDao
        )

    @Provides
    @TVShowsRepo
    fun provideTVShowsRepository(
        networkHandler: NetworkHandler,
        moviesAPI: MoviesAPI,
        moviesDB: MoviesDB,
        tvShowsRemoteKeysDao: TVShowsRemoteKeysDao,
        tvShowsDao: TVShowsDao,
        tvShowClipsDao: TvShowClipsDao,
    ): BaseVideosRepository =
        TVShowsRepository2(
            networkHandler = networkHandler,
            moviesAPI = moviesAPI,
            moviesDB = moviesDB,
            tvShowsRemoteKeysDao = tvShowsRemoteKeysDao,
            tvShowsDao = tvShowsDao,
            tvShowClipsDao = tvShowClipsDao
        )

    @Provides
    @FavoriteMoviesRepo
    fun provideFavoriteMoviesRepository(
        favoriteMoviesDao: FavoriteMoviesDao
    ): BaseFavoriteRepository =
        FavoriteMoviesRepository(favoriteMoviesDao)

    @Provides
    @FavoriteTVShowsRepo
    fun provideFavoriteTVShowsRepository(
        favoriteTVShowsDao: FavoriteTVShowsDao
    ): BaseFavoriteRepository =
        FavoriteTVShowsRepository(favoriteTVShowsDao)

    @Provides
    fun provideNetworkHandler(@ApplicationContext context: Context): NetworkHandler =
        NetworkHandlerImpl(context)

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MoviesRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TVShowsRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FavoriteMoviesRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FavoriteTVShowsRepo