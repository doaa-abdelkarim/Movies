package com.example.movies.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movies.di.MoviesRepository
import com.example.movies.di.TVShowsRepository
import com.example.movies.domain.repositoriescontract.VideosRepositoryContract
import com.example.movies.util.Constants.Companion.PAGE
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException

class RefreshDataWorker(
    @ApplicationContext context: Context, params: WorkerParameters,
    @MoviesRepository private val moviesRepository: VideosRepositoryContract,
    @TVShowsRepository private val tvShowRepository: VideosRepositoryContract
) :
    CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            val movies = moviesRepository.getVideos(PAGE)
            movies.forEach {
                moviesRepository.getVideoDetails(it.id ?: -1)
                moviesRepository.getVideoClips(it.id ?: -1)
                moviesRepository.getVideoReviews(it.id ?: -1, PAGE)
            }

            val tvShows = tvShowRepository.getVideos(PAGE)
            tvShows.forEach {
                tvShowRepository.getVideoDetails(it.id ?: -1)
                tvShowRepository.getVideoClips(it.id ?: -1)
                tvShowRepository.getVideoReviews(it.id ?: -1, PAGE)
            }
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
