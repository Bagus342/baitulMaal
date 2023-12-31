package com.example.baitulmaal.repository

import androidx.lifecycle.LiveData
import com.example.baitulmaal.dao.SavingsDao
import com.example.baitulmaal.model.Dates
import com.example.baitulmaal.model.Savings

class SavingsRepository(private val savingsDao: SavingsDao) {

    val getDateData: LiveData<List<Dates>> = savingsDao.getDateData()
    val getAllData: LiveData<List<Savings>> = savingsDao.getAllData()

    suspend fun addSavings(savings: Savings) {
        savingsDao.addSavings(savings)
    }

    suspend fun deleteData() {
        savingsDao.deleteSavings()
    }

    suspend fun updateData(savings: Savings) {
        savingsDao.updateSavings(savings)
    }

    suspend fun deleteItem(savings: Savings) {
        savingsDao.deleteItemSavings(savings)
    }

}