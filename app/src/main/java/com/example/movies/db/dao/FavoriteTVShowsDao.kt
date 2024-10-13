package com.example.movies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.favorites.DatabaseFavoriteTVShow
import com.example.movies.data.local.models.videos.tvshow.DatabaseTVShow

@Dao
interface FavoriteTVShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(databaseFavoriteTVShow: DatabaseFavoriteTVShow)

    @Query("SELECT t.id, t.posterPath, t.title FROM tv_show_table t JOIN favorite_tv_shows_table f ON t.id = f.videoId ORDER BY f.id")
    fun getAllFavoritesTVShows(): List<DatabaseTVShow>

}