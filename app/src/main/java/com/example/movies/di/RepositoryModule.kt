package com.example.movies.di

import android.content.Context
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.repositoriesimpl.FavoriteMoviesRepositoryImpl
import com.example.movies.data.repositoriesimpl.FavoriteTVShowsRepositoryImpl
import com.example.movies.data.repositoriesimpl.OfflineFavoriteMoviesImpl
import com.example.movies.data.repositoriesimpl.OfflineFavoriteTVShowsImpl
import com.example.movies.data.repositoriesimpl.OfflineTVShowsImpl
import com.example.movies.data.repositoriesimpl.OnlineTVShowsImpl
import com.example.movies.data.repositoriesimpl.TVShowsRepositoryImpl
import com.example.movies.db.dao.FavoriteMoviesDao
import com.example.movies.db.dao.FavoriteTVShowsDao
import com.example.movies.db.dao.MovieClipsDao
import com.example.movies.db.dao.MovieReviewsDao
import com.example.movies.db.dao.MoviesDao
import com.example.movies.db.dao.TVShowsDao
import com.example.movies.db.dao.TvShowClipsDao
import com.example.movies.db.dao.TvShowReviewsDao
import com.example.movies.domain.repositoriescontract.FavoriteRepositoryContract
import com.example.movies.domain.repositoriescontract.MoviesRepositoryImp
import com.example.movies.domain.repositoriescontract.OfflineFavoriteMovies
import com.example.movies.domain.repositoriescontract.OfflineFavoriteTVShows
import com.example.movies.domain.repositoriescontract.OfflineMovies
import com.example.movies.domain.repositoriescontract.OfflineMoviesImpl
import com.example.movies.domain.repositoriescontract.OfflineTVShows
import com.example.movies.domain.repositoriescontract.OnlineMovies
import com.example.movies.domain.repositoriescontract.OnlineMoviesImpl
import com.example.movies.domain.repositoriescontract.OnlineTVShows
import com.example.movies.domain.repositoriescontract.VideosRepositoryContract
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
        onlineMovies: OnlineMovies,
        offlineMovies: OfflineMovies,
        networkHandler: NetworkHandler
    ): VideosRepositoryContract {
        return MoviesRepositoryImp(onlineMovies, offlineMovies, networkHandler)
    }

    @Provides
    fun provideOnlineMovies(moviesAPI: MoviesAPI): OnlineMovies {
        return OnlineMoviesImpl(moviesAPI)
    }

    @Provides
    fun provideOfflineMovies(
        moviesDao: MoviesDao,
        movieClipsDao: MovieClipsDao,
        movieReviewsDao: MovieReviewsDao
    ): OfflineMovies {
        return OfflineMoviesImpl(moviesDao, movieClipsDao, movieReviewsDao)
    }

    @Provides
    @TVShowsRepository
    fun provideTVShowsRepository(
        onlineTVShows: OnlineTVShows,
        offlineTVShows: OfflineTVShows,
        networkHandler: NetworkHandler
    ): VideosRepositoryContract {
        return TVShowsRepositoryImpl(onlineTVShows, offlineTVShows, networkHandler)
    }

    @Provides
    fun provideOnlineTVShows(moviesAPI: MoviesAPI): OnlineTVShows {
        return OnlineTVShowsImpl(moviesAPI)
    }

    @Provides
    fun provideOfflineTVShows(
        tvShowsDao: TVShowsDao,
        tvShowClipsDao: TvShowClipsDao,
        tvShowReviewsDao: TvShowReviewsDao
    ): OfflineTVShows {
        return OfflineTVShowsImpl(tvShowsDao, tvShowClipsDao, tvShowReviewsDao)
    }

    @Provides
    @FavoriteMoviesRepository
    fun provideFavoriteMoviesRepository(
        offlineFavoritesMovies: OfflineFavoriteMovies
    ): FavoriteRepositoryContract {
        return FavoriteMoviesRepositoryImpl(offlineFavoritesMovies)
    }

    @Provides
    fun provideOfflineFavoriteMovies(
        favoriteMoviesDao: FavoriteMoviesDao
    ): OfflineFavoriteMovies {
        return OfflineFavoriteMoviesImpl(favoriteMoviesDao)
    }

    @Provides
    @FavoriteTVShowsRepository
    fun provideFavoriteTVShowsRepository(
        offlineFavoriteTVShows: OfflineFavoriteTVShows
    ): FavoriteRepositoryContract {
        return FavoriteTVShowsRepositoryImpl(offlineFavoriteTVShows)
    }

    @Provides
    fun provideOfflineFavoriteTVShows(
        favoriteTVShowsDao: FavoriteTVShowsDao
    ): OfflineFavoriteTVShows {
        return OfflineFavoriteTVShowsImpl(favoriteTVShowsDao)
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