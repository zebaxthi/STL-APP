package com.uco.stlapp.services

import com.uco.stlapp.models.ArticleResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface WebService {
    @GET("/crfhKA/articles")
    suspend fun getArticles(): Response<List<ArticleResponse>>
}