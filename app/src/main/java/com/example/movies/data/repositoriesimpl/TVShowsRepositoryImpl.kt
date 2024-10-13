package com.example.movies.data.repositoriesimpl

import com.example.movies.data.local.models.videos.tvshow.asDomainModel
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.tvshow.asDomainModel
import com.example.movies.db.dao.TVShowsDao
import com.example.movies.db.dao.TvShowClipsDao
import com.example.movies.db.dao.TvShowReviewsDao
import com.example.movies.domain.models.*
import com.example.movies.domain.repositoriescontract.OfflineTVShows
import com.example.movies.domain.repositoriescontract.OnlineTVShows
import com.example.movies.domain.repositoriescontract.TVShowsRepositoryContract
import com.example.movies.util.NetworkHandler

class TVShowsRepositoryImpl(
    private val onlineTVShows: OnlineTVShows,
    private val offlineTVShows: OfflineTVShows,
    private val networkHandler: NetworkHandler
) : TVShowsRepositoryContract {
    override suspend fun getVideos(page: Int): List<Video> {
        if (networkHandler.isOnline()) {
            val tvShows = onlineTVShows.getVideos(page)
            offlineTVShows.cacheVideos(tvShows)
            return tvShows
        }
        return offlineTVShows.getVideos(page)
    }

    override suspend fun getVideoDetails(videoId: Int): Video {
        if (networkHandler.isOnline()) {
            val tvShow = onlineTVShows.getVideoDetails(videoId)
            offlineTVShows.cacheVideoDetails(tvShow)
            return tvShow
        }
        return offlineTVShows.getVideoDetails(videoId)
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        if (networkHandler.isOnline()) {
            val clips = onlineTVShows.getVideoClips(videoId)
            offlineTVShows.cacheVideoClips(clips)
            return clips
        }
        return offlineTVShows.getVideoClips(videoId)
    }

    override suspend fun getVideoReviews(videoId: Int, page: Int): List<Review> {
        if (networkHandler.isOnline()) {
            val reviews = onlineTVShows.getVideoReviews(videoId, page)
            offlineTVShows.cacheVideoReviews(reviews)
            return reviews
        }
        return offlineTVShows.getVideoReviews(videoId, page)
    }

}

class OnlineTVShowsImpl(private val moviesAPI: MoviesAPI) : OnlineTVShows {
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

class OfflineTVShowsImpl(
    private val tvShowsDao: TVShowsDao,
    private val tvShowClipsDao: TvShowClipsDao,
    private val tvShowReviewsDao: TvShowReviewsDao
) : OfflineTVShows {
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