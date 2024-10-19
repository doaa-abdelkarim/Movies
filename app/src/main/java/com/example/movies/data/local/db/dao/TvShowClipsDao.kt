package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.videos.tvshows.LocalTVShowClip

@Dao
interface TvShowClipsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clips: List<LocalTVShowClip>)

    @Query("SELECT * FROM tv_show_clips_table WHERE videoId = :tvShowId")
    suspend fun getAllClips(tvShowId: Int): List<LocalTVShowClip>

}