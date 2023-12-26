package com.example.baitulmaal

import com.example.baitulmaal.adapter.DateAdapter

class CategorySource(val context: DateAdapter) {
    fun getCategoryList(): Array<String> {
        return context.array
    }
}