package com.uco.stlapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.uco.stlapp.R
import com.uco.stlapp.adapters.ArticleAdapter
import com.uco.stlapp.databinding.ActivityArticleBinding
import com.uco.stlapp.models.Article
import com.uco.stlapp.providers.ArticleProvider

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val manager = LinearLayoutManager(this)
        binding.recyclerArticle.layoutManager = LinearLayoutManager(this)
        binding.recyclerArticle.adapter = ArticleAdapter(ArticleProvider.articleList) { superHero ->
            onItemSelected(
                superHero
            )
        }
    }

    private fun onItemSelected(article: Article){
        Toast.makeText(this, article.name, Toast.LENGTH_SHORT).show()
    }
}