package com.example.movies.db.dao

import androidx.room.*
import com.example.movies.data.local.models.videos.tvshow.DatabaseTVShow

@Dao
interface TVShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShows: List<DatabaseTVShow>)

    @Update
    suspend fun update(databaseTVShow: DatabaseTVShow)

    @Query("SELECT * FROM tv_show_table")
    fun getAllTVShows(): List<DatabaseTVShow>

    @Query("SELECT * FROM tv_show_table where id = :id")
    fun getTVShowById(id: Int): DatabaseTVShow
}