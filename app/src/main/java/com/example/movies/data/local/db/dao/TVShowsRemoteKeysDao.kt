package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.remotekeys.TVShowRemoteKeys

@Dao
interface TVShowsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKeys: List<TVShowRemoteKeys>)

    @Query("SELECT * FROM tv_show_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeysById(id: Int): TVShowRemoteKeys

    @Query("DELETE FROM tv_show_remote_keys_table")
    suspend fun clearRemoteKeys()

}