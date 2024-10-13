package com.example.movies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.videos.tvshow.DatabaseTVShowClip

@Dao
interface TvShowClipsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clips: List<DatabaseTVShowClip>)

    @Query("SELECT * FROM tv_show_clip_table WHERE videoId = :tvShowId")
    fun getAllClips(tvShowId: Int): List<DatabaseTVShowClip>

}