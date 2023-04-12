package com.example.shift.db.dao

import androidx.room.*
import com.example.shift.db.BinItem

@Dao
interface BinItemDao{
    @Query("SELECT * FROM binItems_table")
    suspend fun getItems(): List<BinItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(vararg item: BinItem)

    @Delete
    suspend fun deleteItem(vararg item: BinItem)
}