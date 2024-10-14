package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.favorites.LocalFavoriteTVShow
import com.example.movies.data.local.models.videos.tvshows.LocalTVShow

@Dao
interface FavoriteTVShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(databaseFavoriteTVShow: LocalFavoriteTVShow)

    @Query("SELECT t.id, t.posterPath, t.title FROM tv_show_table t JOIN favorite_tv_shows_table f ON t.id = f.videoId ORDER BY f.id")
    suspend fun getAllFavoritesTVShows(): List<LocalTVShow>

}