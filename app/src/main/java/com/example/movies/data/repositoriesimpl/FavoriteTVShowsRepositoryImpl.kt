package com.example.movies.data.repositoriesimpl

import com.example.movies.data.local.models.favorites.DatabaseFavorite
import com.example.movies.data.local.models.favorites.DatabaseFavoriteTVShow
import com.example.movies.data.local.models.videos.tvshow.asDomainModel
import com.example.movies.db.dao.FavoriteTVShowsDao
import com.example.movies.domain.models.Video
import com.example.movies.domain.repositoriescontract.FavoriteTVShowsRepositoryContract
import com.example.movies.domain.repositoriescontract.OfflineFavoriteTVShows

class FavoriteTVShowsRepositoryImpl(private val offlineFavoriteTVShows: OfflineFavoriteTVShows) :
    FavoriteTVShowsRepositoryContract {

    override suspend fun cacheFavorites(databaseFavorite: DatabaseFavorite) {
        return offlineFavoriteTVShows.cacheFavorites(databaseFavorite)
    }

    override suspend fun getAllFavorites(): List<Video> {
        return offlineFavoriteTVShows.getAllFavorites()
    }

}

class OfflineFavoriteTVShowsImpl(private val favoriteTVShowsDao: FavoriteTVShowsDao) :
    OfflineFavoriteTVShows {

    override suspend fun cacheFavorites(databaseFavorite: DatabaseFavorite) {
        favoriteTVShowsDao.insert(databaseFavorite as DatabaseFavoriteTVShow)
    }

    override suspend fun getAllFavorites(): List<Video> {
        return favoriteTVShowsDao.getAllFavoritesTVShows().asDomainModel()
    }

}