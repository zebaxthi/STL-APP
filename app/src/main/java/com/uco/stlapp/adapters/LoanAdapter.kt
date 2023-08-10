package com.uco.stlapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uco.stlapp.R
import com.uco.stlapp.models.Article
import com.uco.stlapp.models.Loan
import com.uco.stlapp.viewHolders.ArticleViewHolder
import com.uco.stlapp.viewHolders.LoanViewHolder

class LoanAdapter(private val loans:List<Loan>, private val onClickListener: (Loan) -> Unit) : RecyclerView.Adapter<LoanViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LoanViewHolder(layoutInflater.inflate(R.layout.item_loan, parent, false))
    }

    override fun getItemCount(): Int = loans.size


    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        val item = loans[position]
        holder.render(item, onClickListener)
    }
}