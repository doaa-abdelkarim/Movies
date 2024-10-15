package com.example.movies.data.local.datasources

import com.example.movies.data.local.db.dao.TVShowsDao
import com.example.movies.data.local.db.dao.TvShowClipsDao
import com.example.movies.data.local.db.dao.TvShowReviewsDao
import com.example.movies.data.local.models.videos.tvshows.asDomainModel
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.entities.asTVShowClipsDatabaseModel
import com.example.movies.domain.entities.asTVShowDatabaseModel
import com.example.movies.domain.entities.asTVShowReviewsDatabaseModel
import com.example.movies.domain.repositories.BaseVideosRepository

interface BaseTVShowsLocalDataSource : BaseVideosRepository

class TVShowsLocalDataSource(
    private val tvShowsDao: TVShowsDao,
    private val tvShowClipsDao: TvShowClipsDao,
    private val tvShowReviewsDao: TvShowReviewsDao
) : BaseTVShowsLocalDataSource {
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

    override suspend fun getVideoInfo(videoId: Int): Video {
        return tvShowsDao.getTVShowById(videoId).asDomainModel()
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        return tvShowClipsDao.getAllClips(videoId).asDomainModel()
    }

    override suspend fun getVideoReviews(videoId: Int, page: Int): List<Review> {
        return tvShowReviewsDao.getAllReviews(videoId).asDomainModel()
    }

}