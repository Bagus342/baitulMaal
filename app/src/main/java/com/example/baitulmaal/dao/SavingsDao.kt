package com.example.baitulmaal.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.baitulmaal.model.Dates
import com.example.baitulmaal.model.Savings

@Dao
interface SavingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSavings(savings: Savings)

    @Update
    suspend fun updateSavings(savings: Savings)

    @Delete
    suspend fun deleteItemSavings(savings:Savings)

    @Query("DELETE FROM savings")
    suspend fun deleteSavings()
    @Query("SELECT DISTINCT date FROM savings ORDER BY date DESC")
    fun getDateData() : LiveData<List<Dates>>
//
    @Query("SELECT  * FROM savings ORDER BY date DESC")
    fun getAllData() : LiveData<List<Savings>>
}