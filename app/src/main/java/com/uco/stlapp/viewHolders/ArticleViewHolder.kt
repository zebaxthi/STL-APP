package com.uco.stlapp.viewHolders

import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.RecyclerView
import com.uco.stlapp.databinding.ItemArticleBinding
import com.uco.stlapp.models.Article

class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view){
    var binding = ItemArticleBinding.bind(view)

    fun render(articleModel: Article, onClickListener: (Article) -> Unit){
        binding.tvNameArticle.text = articleModel.name
        binding.RefArticle.text = articleModel.ref
        binding.tvQuantityArticle.text = articleModel.quantity.toString()
        binding.tvStatusArticle.text = articleModel.status
        itemView.setOnClickListener{onClickListener(articleModel)}
    }
}