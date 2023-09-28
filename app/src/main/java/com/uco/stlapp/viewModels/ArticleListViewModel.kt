package com.uco.stlapp.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uco.stlapp.models.Article
import com.uco.stlapp.models.toModel
import com.uco.stlapp.repository.dao.ArticleDAO
import com.uco.stlapp.repository.database.AppDatabase
import com.uco.stlapp.repository.entities.toEntity
import com.uco.stlapp.services.ArticleService
import kotlinx.coroutines.launch

class ArticleListViewModel(context: Context) : ViewModel() {
    private var articleDao: ArticleDAO =  AppDatabase.getInstance(context).articleDAO()
    private var articleService = ArticleService()
    fun getArticles(): List<Article> {
        val entities = articleDao.getAll()
        return entities.map { it.toModel() }
    }

     fun fetchArticlesData() {
        viewModelScope.launch {
            val response = articleService.getArticles()
            Log.i("response", response.toString())
            if (response != null && response.isSuccessful){
                response?.body()?.let { list ->
                    var articlesEntities = list!!.map {
                        it.toEntity()
                    }
                    articleDao.deleteAll()
                    articlesEntities.forEach{ article ->
                        articleDao.insertAll(article)
                    }
                }
            }
        }
    }
}