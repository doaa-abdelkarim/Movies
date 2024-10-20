package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.remotekeys.MoviesRemoteKeys

@Dao
interface MoviesRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKeys: List<MoviesRemoteKeys>)

    @Query("SELECT * FROM movies_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeysById(id: Int): MoviesRemoteKeys

    @Query("DELETE FROM movies_remote_keys_table")
    suspend fun clearRemoteKeys()

}