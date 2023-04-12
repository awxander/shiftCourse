package com.example.shifttest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shifttest.db.dao.BinItemDao


@Database(entities = [BinItem::class], version = 1)
abstract class BinItemDatabase : RoomDatabase() {
    abstract fun binItemDao(): BinItemDao

    companion object {
        private const val DB_NAME = "binItems_database"
        @Volatile private var instance: BinItemDatabase? = null

        fun getInstance(context: Context): BinItemDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): BinItemDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                BinItemDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}