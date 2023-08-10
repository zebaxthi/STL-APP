package com.uco.stlapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.uco.stlapp.R
import com.uco.stlapp.adapters.ArticleAdapter
import com.uco.stlapp.adapters.LoanAdapter
import com.uco.stlapp.databinding.ActivityArticleBinding
import com.uco.stlapp.databinding.ActivityLoanBinding
import com.uco.stlapp.models.Article
import com.uco.stlapp.models.Loan
import com.uco.stlapp.providers.ArticleProvider
import com.uco.stlapp.providers.LoanProvider

class LoanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val manager = LinearLayoutManager(this)
        binding.recyclerLoan.layoutManager = LinearLayoutManager(this)
        binding.recyclerLoan.adapter = LoanAdapter(LoanProvider.loanList) { loan ->
            onItemSelected(
                loan
            )
        }
    }

    private fun onItemSelected(loan: Loan){
        Toast.makeText(this, loan.monitorName, Toast.LENGTH_SHORT).show()
    }
}