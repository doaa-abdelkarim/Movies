package com.example.movies.data.local.db.dao

import androidx.room.*
import com.example.movies.data.local.models.videos.tvshows.LocalTVShow

@Dao
interface TVShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShows: List<LocalTVShow>)

    @Update
    suspend fun update(localTVShow: LocalTVShow)

    @Query("SELECT * FROM tv_show_table")
    fun getAllTVShows(): List<LocalTVShow>

    @Query("SELECT * FROM tv_show_table where id = :id")
    fun getTVShowById(id: Int): LocalTVShow
}