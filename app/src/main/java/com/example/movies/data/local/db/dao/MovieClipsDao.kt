package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.videos.movies.LocalMovieClip

@Dao
interface MovieClipsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clips: List<LocalMovieClip>)

    @Query("SELECT * FROM movie_clips_table WHERE videoId = :movieId")
    suspend fun getAllClips(movieId: Int): List<LocalMovieClip>

}