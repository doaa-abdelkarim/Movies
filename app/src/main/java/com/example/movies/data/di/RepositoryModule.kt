package com.example.movies.data.di

import android.content.Context
import com.example.movies.data.local.db.MoviesDB
import com.example.movies.data.local.db.dao.FavoritesDao
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.repositories.FavoritesRepository
import com.example.movies.data.repositories.MoviesRepository2
import com.example.movies.domain.repositories.BaseFavoritesRepository
import com.example.movies.domain.repositories.BaseMoviesRepository
import com.example.movies.util.helpers.NetworkHandler
import com.example.movies.util.helpers.NetworkHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ViewModelComponent::class)
@Module
class RepositoryModule {

    //Use the next code if network is the single source of truth
    /*  @Provides
      fun provideMoviesRepository(
          moviesAPI: MoviesAPI,
      ): MoviesRepository =
          MoviesRepository1(
              moviesAPI = moviesAPI,
          )
     */

    //Use the next code if Room is the single source of truth
    @Provides
    fun provideMoviesRepository(
        moviesAPI: MoviesAPI,
        moviesDB: MoviesDB,
        networkHandler: NetworkHandler
    ): BaseMoviesRepository =
        MoviesRepository2(
            moviesAPI = moviesAPI,
            moviesDB = moviesDB,
            networkHandler = networkHandler
        )

    @Provides
    fun provideFavoritesRepository(
        favoritesDao: FavoritesDao
    ): BaseFavoritesRepository =
        FavoritesRepository(favoritesDao)

    @Provides
    fun provideNetworkHandler(@ApplicationContext context: Context): NetworkHandler =
        NetworkHandlerImpl(context)

}