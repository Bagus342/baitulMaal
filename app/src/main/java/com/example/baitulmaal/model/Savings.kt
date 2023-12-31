package com.example.baitulmaal.model

import androidx.room.*

@Entity(tableName = "savings")
data class Savings (
    @ColumnInfo(name = "money") val moneySaving: Double,
    @ColumnInfo(name = "note") val noteSaving: String,
    @ColumnInfo(name = "category") val categorySaving: String,
    @ColumnInfo(name = "date") val dateSaving: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
        )