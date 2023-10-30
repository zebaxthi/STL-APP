package com.uco.stlapp.repository.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int,
    @ColumnInfo var identification: String,
    @ColumnInfo var name: String,
    @ColumnInfo var lastname: String,
    @ColumnInfo var email: String,
    @ColumnInfo var password: String,
    @ColumnInfo var mobile: String,
    @ColumnInfo var address: String,
    @ColumnInfo var rol: String,
    @ColumnInfo var RFID: String,
)