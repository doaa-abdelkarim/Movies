package com.example.movies.data.di

import android.content.Context
import com.example.movies.data.local.datasources.BaseFavoriteMoviesLocalDataSource
import com.example.movies.data.local.datasources.BaseFavoriteTVShowsLocalDataSource
import com.example.movies.data.local.datasources.BaseMoviesLocalDataSource
import com.example.movies.data.local.datasources.BaseTVShowsLocalDataSource
import com.example.movies.data.local.datasources.FavoriteMoviesLocalDataSource
import com.example.movies.data.local.datasources.FavoriteTVShowsLocalDataSource
import com.example.movies.data.local.datasources.MoviesLocalDataSource
import com.example.movies.data.local.datasources.TVShowsLocalDataSource
import com.example.movies.data.local.db.dao.FavoriteMoviesDao
import com.example.movies.data.local.db.dao.FavoriteTVShowsDao
import com.example.movies.data.local.db.dao.MovieClipsDao
import com.example.movies.data.local.db.dao.MovieReviewsDao
import com.example.movies.data.local.db.dao.MoviesDao
import com.example.movies.data.local.db.dao.TVShowsDao
import com.example.movies.data.local.db.dao.TvShowClipsDao
import com.example.movies.data.local.db.dao.TvShowReviewsDao
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.datasources.BaseMoviesRemoteDataSource
import com.example.movies.data.remote.datasources.BaseTVShowsRemoteDataSource
import com.example.movies.data.remote.datasources.MoviesRemoteDataSource
import com.example.movies.data.remote.datasources.TVShowsRemoteDataSource
import com.example.movies.data.repositories.FavoriteMoviesRepository
import com.example.movies.data.repositories.FavoriteTVShowsRepository
import com.example.movies.data.repositories.MoviesRepository
import com.example.movies.data.repositories.TVShowsRepository
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

    @Provides
    @MoviesRepo
    fun provideMoviesRepository(
        baseMoviesRemoteDataSource: BaseMoviesRemoteDataSource,
        baseMoviesLocalDataSource: BaseMoviesLocalDataSource,
        networkHandler: NetworkHandler
    ): BaseVideosRepository {
        return MoviesRepository(
            baseMoviesRemoteDataSource,
            baseMoviesLocalDataSource,
            networkHandler
        )
    }

    @Provides
    fun provideMoviesRemoteDataSource(moviesAPI: MoviesAPI): BaseMoviesRemoteDataSource {
        return MoviesRemoteDataSource(moviesAPI)
    }

    @Provides
    fun provideMoviesLocalDataSource(
        moviesDao: MoviesDao,
        movieClipsDao: MovieClipsDao,
        movieReviewsDao: MovieReviewsDao
    ): BaseMoviesLocalDataSource {
        return MoviesLocalDataSource(moviesDao, movieClipsDao, movieReviewsDao)
    }

    @Provides
    @TVShowsRepo
    fun provideTVShowsRepository(
        baseTVShowsRemoteDataSource: BaseTVShowsRemoteDataSource,
        baseTVShowsLocalDataSource: BaseTVShowsLocalDataSource,
        networkHandler: NetworkHandler
    ): BaseVideosRepository {
        return TVShowsRepository(
            baseTVShowsRemoteDataSource,
            baseTVShowsLocalDataSource,
            networkHandler
        )
    }

    @Provides
    fun provideTVShowsRemoteDataSource(moviesAPI: MoviesAPI): BaseTVShowsRemoteDataSource {
        return TVShowsRemoteDataSource(moviesAPI)
    }

    @Provides
    fun provideTVShowsLocalDataSource(
        tvShowsDao: TVShowsDao,
        tvShowClipsDao: TvShowClipsDao,
        tvShowReviewsDao: TvShowReviewsDao
    ): BaseTVShowsLocalDataSource {
        return TVShowsLocalDataSource(tvShowsDao, tvShowClipsDao, tvShowReviewsDao)
    }

    @Provides
    @FavoriteMoviesRepo
    fun provideFavoriteMoviesRepository(
        baseFavoriteMoviesLocalDataSource: BaseFavoriteMoviesLocalDataSource
    ): BaseFavoriteRepository {
        return FavoriteMoviesRepository(baseFavoriteMoviesLocalDataSource)
    }

    @Provides
    fun provideFavoriteMoviesLocalDataSource(
        favoriteMoviesDao: FavoriteMoviesDao
    ): BaseFavoriteMoviesLocalDataSource {
        return FavoriteMoviesLocalDataSource(favoriteMoviesDao)
    }

    @Provides
    @FavoriteTVShowsRepo
    fun provideFavoriteTVShowsRepository(
        baseFavoriteTVShowsLocalDataSource: BaseFavoriteTVShowsLocalDataSource
    ): BaseFavoriteRepository {
        return FavoriteTVShowsRepository(baseFavoriteTVShowsLocalDataSource)
    }

    @Provides
    fun provideFavoriteTVShowsLocalDataSource(
        favoriteTVShowsDao: FavoriteTVShowsDao
    ): BaseFavoriteTVShowsLocalDataSource {
        return FavoriteTVShowsLocalDataSource(favoriteTVShowsDao)
    }

    @Provides
    fun provideNetworkHandler(@ApplicationContext context: Context): NetworkHandler {
        return NetworkHandlerImpl(context)
    }

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