package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.remotekeys.MovieReviewsRemoteKeys

@Dao
interface MovieReviewsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKeys: List<MovieReviewsRemoteKeys>)

    @Query("SELECT * FROM movie_reviews_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeysById(id: String): MovieReviewsRemoteKeys

    @Query("DELETE FROM movie_reviews_remote_keys_table")
    suspend fun clearRemoteKeys()

}