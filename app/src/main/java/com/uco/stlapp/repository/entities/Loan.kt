package com.uco.stlapp.repository.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Loan(
    @PrimaryKey val id: Int,
    @ColumnInfo var monitorName: String,
    @ColumnInfo var articleName: String,
    @ColumnInfo var quantityArticle: Int,
    @ColumnInfo var dateStart: Date,
    @ColumnInfo var dateEnd: Date,
    @ColumnInfo var isReturned: Boolean,
)
