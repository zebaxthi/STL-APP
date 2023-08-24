package com.uco.stlapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.uco.stlapp.R
import com.uco.stlapp.adapters.ArticleAdapter
import com.uco.stlapp.databinding.ActivityArticleBinding
import com.uco.stlapp.models.Article
import com.uco.stlapp.providers.ArticleProvider

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding
    private var ArticleMutableList: MutableList<Article> = ArticleProvider.articleList.toMutableList()
    private lateinit var adapter: ArticleAdapter
    private val manager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etFilterArticle.addTextChangedListener { articleFilter ->
            var Filtered = ArticleMutableList.filter { x -> x.name.lowercase().contains(articleFilter.toString().lowercase()) }
            adapter.updateArticles(Filtered)
        }
        initRecyclerView()
    }

    private fun initRecyclerView(){
        adapter = ArticleAdapter(ArticleMutableList){
            onItemSelected(
                it
            )
        }
        binding.recyclerArticle.layoutManager = manager
        binding.recyclerArticle.adapter = adapter
    }

    private fun onItemSelected(article: Article){
        Toast.makeText(this, article.name, Toast.LENGTH_SHORT).show()
    }
}