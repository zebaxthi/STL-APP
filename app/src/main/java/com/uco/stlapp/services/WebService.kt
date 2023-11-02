package com.uco.stlapp.services

import com.uco.stlapp.models.ArticleResponse
import com.uco.stlapp.models.PatchArticleQuantity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface WebService {
    @GET("/crfhKA/articles")
    suspend fun getArticles(): Response<List<ArticleResponse>>

    @PATCH("/crfhKA/articles/{id}")
    suspend fun patchArticle(@Path("id") id: Int, @Body request: PatchArticleQuantity): Response<ArticleResponse>

    @GET("/crfhKA/articles/{id}")
    suspend fun getArticleById(@Path("id")id: Int): Response<ArticleResponse>
}