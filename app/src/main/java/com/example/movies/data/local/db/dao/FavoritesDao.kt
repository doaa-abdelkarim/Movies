package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.favorites.LocalFavorite

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localFavorite: LocalFavorite)

    @Query("SELECT * from favorites_table")
    suspend fun getAllFavorites(): List<LocalFavorite>

}