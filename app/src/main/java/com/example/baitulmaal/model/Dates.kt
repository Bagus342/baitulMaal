package com.example.baitulmaal.model

import androidx.room.*

@Entity(tableName = "savings")
data class Dates(
    @ColumnInfo(name = "date") val dateSaving: String
)
