package com.example.movies.data.di

import android.content.Context
import com.example.movies.data.local.db.dao.FavoriteMoviesDao
import com.example.movies.data.local.db.dao.FavoriteTVShowsDao
import com.example.movies.data.local.db.dao.MovieClipsDao
import com.example.movies.data.local.db.dao.MovieReviewsDao
import com.example.movies.data.local.db.dao.MoviesDao
import com.example.movies.data.local.db.dao.TVShowsDao
import com.example.movies.data.local.db.dao.TvShowClipsDao
import com.example.movies.data.local.db.dao.TvShowReviewsDao
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.repositories.OfflineFavoriteMoviesRepository
import com.example.movies.data.repositories.OfflineFavoriteTVShowsRepository
import com.example.movies.data.repositories.OfflineTVShowsRepository
import com.example.movies.data.repositories.OnlineFavoriteMoviesRepository
import com.example.movies.data.repositories.OnlineFavoriteTVShowsRepository
import com.example.movies.data.repositories.OnlineTVShowsRepository
import com.example.movies.data.repositories.TVShowsRepositoryRepository
import com.example.movies.domain.repositories.BaseFavoriteRepository
import com.example.movies.domain.repositories.BaseOfflineFavoriteMoviesRepository
import com.example.movies.domain.repositories.BaseOfflineFavoriteTVShowsRepository
import com.example.movies.domain.repositories.BaseOfflineMoviesRepository
import com.example.movies.domain.repositories.BaseOfflineTVShowsRepository
import com.example.movies.domain.repositories.BaseOnlineMoviesRepository
import com.example.movies.domain.repositories.BaseOnlineTVShowsRepository
import com.example.movies.domain.repositories.BaseVideosRepositoryRepository
import com.example.movies.domain.repositories.MoviesRepositoryRepository
import com.example.movies.domain.repositories.OfflineMoviesRepository
import com.example.movies.domain.repositories.OnlineMoviesRepository
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
    @MoviesRepository
    fun provideMoviesRepository(
        baseOnlineMoviesRepository: BaseOnlineMoviesRepository,
        baseOfflineMoviesRepository: BaseOfflineMoviesRepository,
        networkHandler: NetworkHandler
    ): BaseVideosRepositoryRepository {
        return MoviesRepositoryRepository(
            baseOnlineMoviesRepository,
            baseOfflineMoviesRepository,
            networkHandler
        )
    }

    @Provides
    fun provideOnlineMovies(moviesAPI: MoviesAPI): BaseOnlineMoviesRepository {
        return OnlineMoviesRepository(moviesAPI)
    }

    @Provides
    fun provideOfflineMovies(
        moviesDao: MoviesDao,
        movieClipsDao: MovieClipsDao,
        movieReviewsDao: MovieReviewsDao
    ): BaseOfflineMoviesRepository {
        return OfflineMoviesRepository(moviesDao, movieClipsDao, movieReviewsDao)
    }

    @Provides
    @TVShowsRepository
    fun provideTVShowsRepository(
        baseOnlineTVShowsRepository: BaseOnlineTVShowsRepository,
        baseOfflineTVShowsRepository: BaseOfflineTVShowsRepository,
        networkHandler: NetworkHandler
    ): BaseVideosRepositoryRepository {
        return TVShowsRepositoryRepository(
            baseOnlineTVShowsRepository,
            baseOfflineTVShowsRepository,
            networkHandler
        )
    }

    @Provides
    fun provideOnlineTVShows(moviesAPI: MoviesAPI): BaseOnlineTVShowsRepository {
        return OnlineTVShowsRepository(moviesAPI)
    }

    @Provides
    fun provideOfflineTVShows(
        tvShowsDao: TVShowsDao,
        tvShowClipsDao: TvShowClipsDao,
        tvShowReviewsDao: TvShowReviewsDao
    ): BaseOfflineTVShowsRepository {
        return OfflineTVShowsRepository(tvShowsDao, tvShowClipsDao, tvShowReviewsDao)
    }

    @Provides
    @FavoriteMoviesRepository
    fun provideFavoriteMoviesRepository(
        baseOfflineFavoriteMoviesRepository: BaseOfflineFavoriteMoviesRepository
    ): BaseFavoriteRepository {
        return OnlineFavoriteMoviesRepository(baseOfflineFavoriteMoviesRepository)
    }

    @Provides
    fun provideOfflineFavoriteMovies(
        favoriteMoviesDao: FavoriteMoviesDao
    ): BaseOfflineFavoriteMoviesRepository {
        return OfflineFavoriteMoviesRepository(favoriteMoviesDao)
    }

    @Provides
    @FavoriteTVShowsRepository
    fun provideFavoriteTVShowsRepository(
        baseOfflineFavoriteTVShowsRepository: BaseOfflineFavoriteTVShowsRepository
    ): BaseFavoriteRepository {
        return OnlineFavoriteTVShowsRepository(baseOfflineFavoriteTVShowsRepository)
    }

    @Provides
    fun provideOfflineFavoriteTVShows(
        favoriteTVShowsDao: FavoriteTVShowsDao
    ): BaseOfflineFavoriteTVShowsRepository {
        return OfflineFavoriteTVShowsRepository(favoriteTVShowsDao)
    }

    @Provides
    fun provideNetworkHandler(@ApplicationContext context: Context): NetworkHandler {
        return NetworkHandlerImpl(context)
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MoviesRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TVShowsRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FavoriteMoviesRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FavoriteTVShowsRepository