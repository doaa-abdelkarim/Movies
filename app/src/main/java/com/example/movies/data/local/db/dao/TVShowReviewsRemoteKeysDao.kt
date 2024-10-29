package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.remotekeys.TVShowReviewsRemoteKeys

@Dao
interface TVShowReviewsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKeys: List<TVShowReviewsRemoteKeys>)

    @Query("SELECT * FROM tv_show_reviews_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeysById(id: String): TVShowReviewsRemoteKeys

    @Query("DELETE FROM tv_show_reviews_remote_keys_table  WHERE id = :id")
    suspend fun clearRemoteKeys(id: Int)

}