package com.uco.stlapp.models

data class Article(
    val name: String,
    val ref: String,
    val quantity: Int,
    val status: String,
    val photo: String?,
)

fun com.uco.stlapp.repository.entities.Article.toModel(): Article {
    return Article(this.name, this.ref, this.quantity, this.status, this.photo)
}