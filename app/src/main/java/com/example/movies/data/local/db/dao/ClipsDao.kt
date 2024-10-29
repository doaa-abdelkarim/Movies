package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.LocalClip

@Dao
interface ClipsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clips: List<LocalClip>)

    @Query("SELECT * FROM clips_table WHERE movieId = :id")
    suspend fun getClips(id: Int): List<LocalClip>

}