package com.example.baitulmaal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.baitulmaal.dao.SavingsDao
import com.example.baitulmaal.model.Savings

@Database(entities = [Savings::class], version = 1)
abstract class SavingsDatabase : RoomDatabase() {

    abstract fun savingsDao(): SavingsDao

    companion object {
        @Volatile
        private var INSTANCE: SavingsDatabase? = null

        fun getDatabase(context: Context): SavingsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SavingsDatabase::class.java,
                    "savings_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}