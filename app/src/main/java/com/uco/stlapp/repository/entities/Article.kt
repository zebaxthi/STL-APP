package com.uco.stlapp.repository.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article(
    @PrimaryKey val id: Int,
    @ColumnInfo var name: String,
    @ColumnInfo var ref: String,
    @ColumnInfo var quantity: Int,
    @ColumnInfo var status: String,
    @ColumnInfo var photo: String?,
)
