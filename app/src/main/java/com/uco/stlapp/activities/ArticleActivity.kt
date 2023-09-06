package com.uco.stlapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.add
import com.uco.stlapp.adapters.ArticleAdapter
import com.uco.stlapp.databinding.ActivityArticleBinding
import com.uco.stlapp.fragment.ArticleFragment
import com.uco.stlapp.fragment.ArticleFragment.Companion.NAMEARTICLE_BUNDLE
import com.uco.stlapp.fragment.ArticleFragment.Companion.NAMEQUANTITY_BUNDLE
import com.uco.stlapp.fragment.ArticleFragment.Companion.NAMEREF_BUNDLE
import com.uco.stlapp.fragment.ArticleFragment.Companion.NAMESTATUS_BUNDLE
import com.uco.stlapp.fragment.LoanFragment
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
        val bundle= bundleOf(NAMEARTICLE_BUNDLE to article.name,
            NAMEREF_BUNDLE to article.ref, NAMEQUANTITY_BUNDLE to article.quantity,
            NAMESTATUS_BUNDLE to article.status
            )
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            add<ArticleFragment>(binding.fragmentArticle.id, args = bundle)
        }

    }
}