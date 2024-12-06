package com.example.movies.data.di

import android.app.Application
import androidx.room.Room
import com.example.movies.data.local.db.MoviesDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, MoviesDB::class.java, "movies_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideMoviesDao(db: MoviesDB) = db.moviesDao()

    @Provides
    fun provideMovieClipsDao(db: MoviesDB) = db.clipsDao()

    @Provides
    fun provideMovieReviewsDao(db: MoviesDB) = db.reviewsDao()

    @Provides
    fun provideFavoritesDao(db: MoviesDB) = db.favoritesDao()

    @Provides
    fun provideMoviesRemoteKeysDao(db: MoviesDB) = db.moviesRemoteKeysDao()

    @Provides
    fun provideTVShowsRemoteKeysDao(db: MoviesDB) = db.tvShowsRemoteKeysDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope