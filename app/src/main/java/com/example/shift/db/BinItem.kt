package com.example.shifttest.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "binItems_table")
data class BinItem(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val binNum : Long,
    val searchDate : String
)
