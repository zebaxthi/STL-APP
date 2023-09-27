package com.uco.stlapp.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.uco.stlapp.models.Article
import com.uco.stlapp.models.toModel
import com.uco.stlapp.repository.dao.ArticleDAO
import com.uco.stlapp.repository.database.AppDatabase

class ArticleListViewModel(context: Context) : ViewModel() {
        private var articleDao: ArticleDAO =  AppDatabase.getInstance(context).articleDAO()
    fun getArticles(): List<Article> {
        val entities = articleDao.getAll()
        return entities.map { it.toModel() }
    }
}