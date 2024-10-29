package com.example.movies.worker

import android.content.Context
import androidx.paging.map
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movies.domain.repositories.BaseMoviesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException

class RefreshDataWorker(
    @ApplicationContext context: Context, params: WorkerParameters,
    private val baseMoviesRepository: BaseMoviesRepository,
) :
    CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            val movies = baseMoviesRepository.getMovies()
            movies.collect {
                it.map { video ->
                    baseMoviesRepository.getMovieInfo(video.id)
                    baseMoviesRepository.getMovieClips(video.id)
                    baseMoviesRepository.getMovieReviews(video.id)
                }
            }

            val tvShows = baseMoviesRepository.getTVShows()
            tvShows.collect {
                it.map { video ->
                    baseMoviesRepository
                        .getMovieInfo(video.id)
                    baseMoviesRepository
                        .getMovieClips(video.id)
                    baseMoviesRepository
                        .getMovieReviews(video.id)
                }
            }
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
