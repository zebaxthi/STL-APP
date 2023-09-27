package com.uco.stlapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.uco.stlapp.R
import com.uco.stlapp.databinding.FragmentArticleBinding





class ArticleFragment : Fragment() {

    private var nameArticle: String? = null
    private var nameRef: String? = null
    private var nameQuantity: Int? = null
    private var nameStatus: String? = null
    private lateinit var binding: FragmentArticleBinding //ref de vista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentArticleBinding.inflate(layoutInflater)
        arguments?.let {
            nameArticle = it.getString(LoanFragment.NAMEARTICLE_BUNDLE)
            nameRef = it.getString(LoanFragment.NAMEREF_BUNDLE)
            nameQuantity = it.getInt(LoanFragment.NAMEQUANTITY_BUNDLE)
            nameStatus = it.getString(LoanFragment.NAMESTATUS_BUNDLE)
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
        return binding.root
    }

    companion object {
        const val NAMEARTICLE_BUNDLE = "nameArticle_bundle"
        const val NAMEREF_BUNDLE = "nameRef_bundle"
        const val NAMEQUANTITY_BUNDLE = "nameQuantity_bundle"
        const val NAMESTATUS_BUNDLE = "nameStatus_bundle"
        @JvmStatic
        fun newInstance(nameArticle: String, nameRef: String,
                        nameQuantity: Int) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString(LoanFragment.NAMEARTICLE_BUNDLE, nameArticle)
                    putString(LoanFragment.NAMEREF_BUNDLE, nameRef)
                    putInt(LoanFragment.NAMEQUANTITY_BUNDLE, nameQuantity)
                    putString(LoanFragment.NAMESTATUS_BUNDLE, nameStatus)
                }
            }
    }
}