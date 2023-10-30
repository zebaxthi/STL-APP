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

fun com.uco.stlapp.models.ArticleResponse.toEntity(): Article {
    return Article(this.id, this.name, this.ref, this.quantity, this.status, this.photo)
}
