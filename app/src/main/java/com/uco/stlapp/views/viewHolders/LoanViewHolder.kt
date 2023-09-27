package com.uco.stlapp.views.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uco.stlapp.databinding.ItemLoanBinding
import com.uco.stlapp.models.Loan
import java.text.SimpleDateFormat

class LoanViewHolder (view : View) : RecyclerView.ViewHolder(view) {
    var binding = ItemLoanBinding.bind(view)

    fun render(loanModel: Loan, onClickListener: (Loan) -> Unit){
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        binding.tvArticleName.text = loanModel.articleName
        binding.tvMonitorName.text = "Monitor: " + loanModel.monitorName
        binding.tvQuantityArticle.text = "Quantity: " + loanModel.quantityArticle.toString()
        binding.tvDateStart.text = "Start: " + dateFormat.format(loanModel.dateStart)
        binding.tvDateEnd.text = "End: " + dateFormat.format(loanModel.dateEnd)
        binding.tvIsReturned.text = loanModel.isReturned.toString()
        itemView.setOnClickListener{onClickListener(loanModel)}
    }
}