package com.uco.stlapp.viewHolders

import android.renderscript.ScriptGroup.Binding
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uco.stlapp.databinding.ItemLoanBinding
import com.uco.stlapp.models.Article
import com.uco.stlapp.models.Loan

class LoanViewHolder (view : View) : RecyclerView.ViewHolder(view) {
    var binding = ItemLoanBinding.bind(view)

    fun render(loanModel: Loan, onClickListener: (Loan) -> Unit){
        binding.tvArticleName.text = loanModel.articleName
        binding.tvMonitorName.text = loanModel.monitorName
        binding.tvQuantityArticle.text = loanModel.quantityArticle.toString()
        binding.tvDateStart.text = binding.tvDateStart.text.toString() + loanModel.dateStart.toString()
        binding.tvDateEnd.text = binding.tvDateEnd.text.toString() + loanModel.dateEnd.toString()
        binding.tvIsReturned.text = loanModel.isReturned.toString()
        itemView.setOnClickListener{onClickListener(loanModel)}
    }
}