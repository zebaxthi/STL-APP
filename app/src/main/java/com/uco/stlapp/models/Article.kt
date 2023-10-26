package com.uco.stlapp.models

data class Article(
    val id: Int,
    val name: String,
    val ref: String,
    val quantity: Int,
    val status: String,
    val photo: String?,
)

data class PatchArticleQuantity(
    val quantity: Int,
)

fun com.uco.stlapp.repository.entities.Article.toModel(): Article {
    return Article(this.id, this.name, this.ref, this.quantity, this.status, this.photo)
}