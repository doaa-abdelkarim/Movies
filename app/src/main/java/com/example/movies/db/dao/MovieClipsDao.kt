package com.example.movies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.videos.movie.DatabaseMovieClip

@Dao
interface MovieClipsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clips: List<DatabaseMovieClip>)

    @Query("SELECT * FROM movie_clip_table WHERE videoId = :movieId")
    fun getAllClips(movieId: Int): List<DatabaseMovieClip>

}