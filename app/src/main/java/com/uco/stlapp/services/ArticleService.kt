package com.uco.stlapp.services

import com.uco.stlapp.models.PatchArticleQuantity
class ArticleService {
    private  var apiService: WebService? = null

    init {
        apiService = RetrofitClient.getClient?.create(WebService::class.java)
    }

    suspend fun getArticles() = apiService?.getArticles()

    suspend fun patchArticle(id: Int, request: PatchArticleQuantity) = apiService?.patchArticle(id, request)

    suspend fun getArticleById(id:Int)=apiService?.getArticleById(id)
}