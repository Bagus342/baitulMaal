package com.example.baitulmaal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.baitulmaal.SavingsDatabase
import com.example.baitulmaal.model.Dates
import com.example.baitulmaal.model.Savings
import com.example.baitulmaal.repository.SavingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavingsViewModel(application: Application) : AndroidViewModel(application) {

    val getDateData: LiveData<List<Dates>>
    val getAllData: LiveData<List<Savings>>
    private val repository: SavingsRepository

    init {
        val savingsDao = SavingsDatabase.getDatabase(application).savingsDao()
        repository = SavingsRepository(savingsDao)
        getDateData = repository.getDateData
        getAllData = repository.getAllData
    }

    fun addData(savings: Savings) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSavings(savings)
        }
    }

    fun deleteData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData()
        }
    }

    fun updateData(savings: Savings) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(savings)
        }
    }

    fun deleteItemSavings(savings: Savings) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(savings)
        }
    }
}