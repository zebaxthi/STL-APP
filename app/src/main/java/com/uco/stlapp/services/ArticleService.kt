package com.uco.stlapp.services

class ArticleService {
    private  var apiService: WebService? = null

    init {
        apiService = RetrofitClient.getClient?.create(WebService::class.java)
    }

    suspend fun getArticles() = apiService?.getArticles()
}