package com.uco.stlapp.adapters

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uco.stlapp.R
import com.uco.stlapp.models.Article
import com.uco.stlapp.viewHolders.ArticleViewHolder

class ArticleAdapter(private val articles:List<Article>, private val onClickListener: (Article) -> Unit) : RecyclerView.Adapter<ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ArticleViewHolder(layoutInflater.inflate(R.layout.item_article, parent, false))
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = articles[position]
        holder.render(item, onClickListener)
    }
}