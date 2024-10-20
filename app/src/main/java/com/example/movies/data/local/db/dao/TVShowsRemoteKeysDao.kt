package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.remotekeys.TVShowsRemoteKeys

@Dao
interface TVShowsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKeys: List<TVShowsRemoteKeys>)

    @Query("SELECT * FROM tv_shows_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeysById(id: Int): TVShowsRemoteKeys

    @Query("DELETE FROM tv_shows_remote_keys_table")
    suspend fun clearRemoteKeys()

}