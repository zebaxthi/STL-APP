package com.uco.stlapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.uco.stlapp.R
import com.uco.stlapp.views.adapters.ArticleAdapter
import com.uco.stlapp.databinding.FragmentArticleListBinding
import com.uco.stlapp.views.fragments.ArticleFragment.Companion.NAMEARTICLE_BUNDLE
import com.uco.stlapp.views.fragments.ArticleFragment.Companion.NAMEQUANTITY_BUNDLE
import com.uco.stlapp.views.fragments.ArticleFragment.Companion.NAMESTATUS_BUNDLE
import com.uco.stlapp.views.fragments.ArticleFragment.Companion.NAMEREF_BUNDLE
import com.uco.stlapp.models.Article
import com.uco.stlapp.viewModels.ArticleListViewModel
import com.uco.stlapp.views.fragments.ArticleFragment.Companion.NAMEID_BUNDLE


class ArticleListFragment : Fragment() {

    private lateinit var binding: FragmentArticleListBinding
    private var ArticleMutableList: MutableList<Article> = mutableListOf()
    private lateinit var adapter: ArticleAdapter
    private val manager by lazy { LinearLayoutManager(requireContext()) }

    private lateinit var swipeRefreshLayout : SwipeRefreshLayout

    companion object {
        fun newInstance() = ArticleListFragment()
    }

    private lateinit var viewModel: ArticleListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        viewModel = ArticleListViewModel(requireContext())
        ArticleMutableList = viewModel.getArticles().toMutableList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etFilterArticle.addTextChangedListener { articleFilter ->
            val filtered = ArticleMutableList.filter { x ->
                x.name.lowercase().contains(articleFilter.toString().lowercase())
            }
            adapter.updateArticles(filtered)
        }
        initRecyclerView()
        swipeRefreshLayout = binding.swipeRefreshArticles
        swipeRefreshLayout.setOnRefreshListener {
            ArticleMutableList = viewModel.getArticles().toMutableList()
            initRecyclerView()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initRecyclerView() {
        adapter = ArticleAdapter(ArticleMutableList) {
            onItemSelected(it)
        }
        binding.recyclerArticle.layoutManager = manager
        binding.recyclerArticle.adapter = adapter
    }

    private fun onItemSelected(article: Article) {
        val bundle = bundleOf(
            NAMEARTICLE_BUNDLE to article.name,
            NAMEREF_BUNDLE to article.ref,
            NAMEQUANTITY_BUNDLE to article.quantity,
            NAMESTATUS_BUNDLE to article.status,
            NAMEID_BUNDLE to article.id
        )
        findNavController().navigate(R.id.action_nav_articleList_to_articleFragment, bundle)
    }
}