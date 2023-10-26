package com.uco.stlapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.uco.stlapp.R
import com.uco.stlapp.databinding.FragmentArticleBinding
import com.uco.stlapp.models.PatchArticleQuantity
import com.uco.stlapp.services.ArticleService
import com.uco.stlapp.viewModels.ArticleListViewModel
import com.uco.stlapp.views.adapters.ArticleAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ArticleFragment : Fragment() {

    private var nameId: Int? = null
    private var nameArticle: String? = null
    private var nameRef: String? = null
    private var nameQuantity: Int? = null
    private var nameStatus: String? = null
    private lateinit var binding: FragmentArticleBinding //ref de vista
    private var articleService = ArticleService()
    private lateinit var viewModel: ArticleListViewModel
    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentArticleBinding.inflate(layoutInflater)
        arguments?.let {
            nameArticle = it.getString(NAMEARTICLE_BUNDLE)
            nameRef = it.getString(NAMEREF_BUNDLE)
            nameQuantity = it.getInt(NAMEQUANTITY_BUNDLE)
            nameStatus = it.getString(NAMESTATUS_BUNDLE)
            nameId= it.getInt(NAMEID_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        binding.tvNameArticle.text = nameArticle
        binding.tvNameRef.text = "Ref: " + nameRef
        binding.tvNameQuantity.text = "Quantity: " + nameQuantity.toString()
        binding.tvNameStatus.text = "Status: " + nameStatus
        binding.btReturnItem.setOnClickListener{
            findNavController().navigate(R.id.action_articleFragment_to_nav_articleList)
        }
        binding.btLendItem.setOnClickListener{
            val id: Int = nameId ?: 0
            val request : PatchArticleQuantity = (nameQuantity ?: 0).let { PatchArticleQuantity(it - 1) }
            CoroutineScope(Dispatchers.IO).launch {
                articleService.patchArticle(id, request)
                viewModel = ArticleListViewModel(requireContext())
                viewModel.fetchArticlesData()
            }
            findNavController().navigate(R.id.action_articleFragment_to_nav_articleList)
        }
        return binding.root
    }

    companion object {
        const val NAMEID_BUNDLE = "nameId_bundle"
        const val NAMEARTICLE_BUNDLE = "nameArticle_bundle"
        const val NAMEREF_BUNDLE = "nameRef_bundle"
        const val NAMEQUANTITY_BUNDLE = "nameQuantity_bundle"
        const val NAMESTATUS_BUNDLE = "nameStatus_bundle"
        @JvmStatic
        fun newInstance(nameArticle: String, nameRef: String,
                        nameQuantity: Int, nameId: Int, nameStatus: String) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putInt(NAMEID_BUNDLE, nameId)
                    putString(NAMEARTICLE_BUNDLE, nameArticle)
                    putString(NAMEREF_BUNDLE, nameRef)
                    putInt(NAMEQUANTITY_BUNDLE, nameQuantity)
                    putString(NAMESTATUS_BUNDLE, nameStatus)
                }
            }
    }
}