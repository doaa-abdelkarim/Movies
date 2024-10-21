package com.example.movies.data.di

import android.content.Context
import com.example.movies.data.local.db.MoviesDB
import com.example.movies.data.local.db.dao.FavoriteMoviesDao
import com.example.movies.data.local.db.dao.FavoriteTVShowsDao
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

    //Use the next code if network is the single source of truth
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

    //Use the next code if Room is the single source of truth
    @Provides
    @MoviesRepo
    fun provideMoviesRepository(
        moviesAPI: MoviesAPI,
        moviesDB: MoviesDB,
        networkHandler: NetworkHandler
    ): BaseVideosRepository =
        MoviesRepository2(
            moviesAPI = moviesAPI,
            moviesDB = moviesDB,
            networkHandler = networkHandler
        )

    @Provides
    @TVShowsRepo
    fun provideTVShowsRepository(
        moviesAPI: MoviesAPI,
        moviesDB: MoviesDB,
        networkHandler: NetworkHandler
    ): BaseVideosRepository =
        TVShowsRepository2(
            moviesAPI = moviesAPI,
            moviesDB = moviesDB,
            networkHandler = networkHandler
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