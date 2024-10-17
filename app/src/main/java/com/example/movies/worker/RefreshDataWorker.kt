package com.example.movies.worker

import android.content.Context
import androidx.paging.map
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movies.data.di.MoviesRepo
import com.example.movies.data.di.TVShowsRepo
import com.example.movies.domain.repositories.BaseVideosRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException

class RefreshDataWorker(
    @ApplicationContext context: Context, params: WorkerParameters,
    @MoviesRepo private val moviesRepository: BaseVideosRepository,
    @TVShowsRepo private val tvShowRepository: BaseVideosRepository
) :
    CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            val movies = moviesRepository.getVideos()
            movies.collect {
                it.map { video ->
                    moviesRepository.getVideoInfo(video.id ?: -1)
                    moviesRepository.getVideoClips(video.id ?: -1)
                    moviesRepository.getVideoReviews(video.id ?: -1)
                }
            }

            val tvShows = tvShowRepository.getVideos()
            tvShows.collect {
                it.map { video ->
                    tvShowRepository.getVideoInfo(video.id ?: -1)
                    tvShowRepository.getVideoClips(video.id ?: -1)
                    tvShowRepository.getVideoReviews(video.id ?: -1)
                }
            }
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
