package com.example.baitulmaal

import androidx.recyclerview.widget.RecyclerView

class DateSource(val context: RecyclerView) {
    fun getDateList(): Array<String> {
        return context.resources.getStringArray(R.array.dateArray)
    }
}