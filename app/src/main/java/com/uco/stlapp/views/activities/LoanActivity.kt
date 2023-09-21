package com.uco.stlapp.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.uco.stlapp.views.adapters.LoanAdapter
import com.uco.stlapp.databinding.ActivityLoanBinding
import com.uco.stlapp.views.fragments.LoanFragment
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMEARTICLE_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMEENDDATE_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMEISRETURNED_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMEMONITOR_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMEQUANTITY_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMEREF_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMESTARTDATE_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMESTATUS_BUNDLE
import com.uco.stlapp.models.Loan
import com.uco.stlapp.providers.LoanProvider

class LoanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoanBinding
    private var LoanMutableList: MutableList<Loan> = LoanProvider.loanList.toMutableList()
    private lateinit var adapter: LoanAdapter
    private val manager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etFilterLoan.addTextChangedListener { loanFilter ->
            var Filtered = LoanMutableList.filter { x -> x.articleName.lowercase().contains(loanFilter.toString().lowercase()) }
            adapter.updateLoans(Filtered)
        }

        initRecyclerView()
    }

    private fun initRecyclerView(){
        adapter = LoanAdapter(LoanMutableList){
            onItemSelected(
                it
            )
        }
        binding.recyclerLoan.layoutManager = manager
        binding.recyclerLoan.adapter = adapter
    }

    private fun onItemSelected(loan: Loan){
        val bundle = bundleOf(NAMEARTICLE_BUNDLE to loan.articleName,
            NAMEREF_BUNDLE to "", NAMEQUANTITY_BUNDLE to loan.quantityArticle,
            NAMESTATUS_BUNDLE to "", NAMESTARTDATE_BUNDLE to loan.dateStart.toString(),
            NAMEENDDATE_BUNDLE to loan.dateEnd.toString(), NAMEISRETURNED_BUNDLE to loan.isReturned,
            NAMEMONITOR_BUNDLE to loan.monitorName)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            add<LoanFragment>(binding.fragmentLoan.id, args = bundle)
        }
    }
}