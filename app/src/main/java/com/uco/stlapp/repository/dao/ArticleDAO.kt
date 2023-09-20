package com.uco.stlapp.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.uco.stlapp.repository.entities.Article

@Dao
interface ArticleDAO {
    @Query("SELECT * FROM article")
    fun getAll(): List<Article>

    @Query("SELECT * FROM article WHERE id = :id")
    fun findById(id: Int): Article

    @Insert
    fun insertAll(vararg articles: Article)

    @Update
    fun update(article: Article)

    @Delete
    fun delete(article: Article)

    @Query("DELETE FROM Article")
    fun deleteAll()

}