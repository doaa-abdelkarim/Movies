package com.example.movies.data.repositories

import com.example.movies.data.local.db.dao.TVShowsDao
import com.example.movies.data.local.db.dao.TvShowClipsDao
import com.example.movies.data.local.db.dao.TvShowReviewsDao
import com.example.movies.data.local.models.videos.tvshows.asDomainModel
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.tvshow.asDomainModel
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.entities.asTVShowClipsDatabaseModel
import com.example.movies.domain.entities.asTVShowDatabaseModel
import com.example.movies.domain.entities.asTVShowReviewsDatabaseModel
import com.example.movies.domain.repositories.BaseOfflineTVShowsRepository
import com.example.movies.domain.repositories.BaseOnlineTVShowsRepository
import com.example.movies.domain.repositories.BaseTVShowsRepositoryRepository
import com.example.movies.util.NetworkHandler

class TVShowsRepositoryRepository(
    private val baseOnlineTVShowsRepository: BaseOnlineTVShowsRepository,
    private val baseOfflineTVShowsRepository: BaseOfflineTVShowsRepository,
    private val networkHandler: NetworkHandler
) : BaseTVShowsRepositoryRepository {
    override suspend fun getVideos(page: Int): List<Video> {
        if (networkHandler.isOnline()) {
            val tvShows = baseOnlineTVShowsRepository.getVideos(page)
            baseOfflineTVShowsRepository.cacheVideos(tvShows)
            return tvShows
        }
        return baseOfflineTVShowsRepository.getVideos(page)
    }

    override suspend fun getVideoDetails(videoId: Int): Video {
        if (networkHandler.isOnline()) {
            val tvShow = baseOnlineTVShowsRepository.getVideoDetails(videoId)
            baseOfflineTVShowsRepository.cacheVideoDetails(tvShow)
            return tvShow
        }
        return baseOfflineTVShowsRepository.getVideoDetails(videoId)
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        if (networkHandler.isOnline()) {
            val clips = baseOnlineTVShowsRepository.getVideoClips(videoId)
            baseOfflineTVShowsRepository.cacheVideoClips(clips)
            return clips
        }
        return baseOfflineTVShowsRepository.getVideoClips(videoId)
    }

    override suspend fun getVideoReviews(videoId: Int, page: Int): List<Review> {
        if (networkHandler.isOnline()) {
            val reviews = baseOnlineTVShowsRepository.getVideoReviews(videoId, page)
            baseOfflineTVShowsRepository.cacheVideoReviews(reviews)
            return reviews
        }
        return baseOfflineTVShowsRepository.getVideoReviews(videoId, page)
    }

}

class OnlineTVShowsRepository(
    private val moviesAPI: MoviesAPI
) : BaseOnlineTVShowsRepository {
    override suspend fun getVideos(page: Int): List<Video> {
        return moviesAPI.getTVShows(page).asDomainModel()
    }

    override suspend fun getVideoDetails(videoId: Int): Video {
        return moviesAPI.getTVShowDetails(videoId).asDomainModel()
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        return moviesAPI.getTVShowClips(videoId).asDomainModel()
    }

    override suspend fun getVideoReviews(videoId: Int, page: Int): List<Review> {
        return moviesAPI.getTVShowReviews(videoId, page).asDomainModel()
    }

}

class OfflineTVShowsRepository(
    private val tvShowsDao: TVShowsDao,
    private val tvShowClipsDao: TvShowClipsDao,
    private val tvShowReviewsDao: TvShowReviewsDao
) : BaseOfflineTVShowsRepository {
    override suspend fun cacheVideos(videos: List<Video>) {
        tvShowsDao.insert(videos.asTVShowDatabaseModel())
    }

    override suspend fun cacheVideoDetails(video: Video) {
        tvShowsDao.update(video.asTVShowDatabaseModel())
    }

    override suspend fun cacheVideoClips(clips: List<Clip>) {
        tvShowClipsDao.insert(clips.asTVShowClipsDatabaseModel())
    }

    override suspend fun cacheVideoReviews(reviews: List<Review>) {
        tvShowReviewsDao.insert(reviews.asTVShowReviewsDatabaseModel())
    }

    override suspend fun getVideos(page: Int): List<Video> {
        return tvShowsDao.getAllTVShows().asDomainModel()
    }

    override suspend fun getVideoDetails(videoId: Int): Video {
        return tvShowsDao.getTVShowById(videoId).asDomainModel()
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        return tvShowClipsDao.getAllClips(videoId).asDomainModel()
    }

    override suspend fun getVideoReviews(videoId: Int, page: Int): List<Review> {
        return tvShowReviewsDao.getAllReviews(videoId).asDomainModel()
    }

}