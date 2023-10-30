package com.uco.stlapp.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMEENDDATE_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMEMONITOR_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMEISRETURNED_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMEARTICLE_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMEQUANTITY_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMESTARTDATE_BUNDLE
import com.uco.stlapp.views.fragments.LoanFragment.Companion.NAMESTATUS_BUNDLE
import com.uco.stlapp.R
import com.uco.stlapp.databinding.FragmentLoanListBinding
import com.uco.stlapp.models.Loan
import com.uco.stlapp.viewModels.LoanListViewModel
import com.uco.stlapp.views.adapters.LoanAdapter

class LoanListFragment : Fragment() {

    private lateinit var binding: FragmentLoanListBinding
    private var LoanMutableList: MutableList<Loan> = mutableListOf()
    private lateinit var adapter: LoanAdapter
    private val manager by lazy { LinearLayoutManager(requireContext()) }
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout


    companion object {
        fun newInstance() = LoanListFragment()
    }

    private lateinit var viewModel: LoanListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoanListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etFilterLoan.addTextChangedListener { loanFilter ->
            val filtered = LoanMutableList.filter { x ->
                x.articleName.lowercase().contains(loanFilter.toString().lowercase())
            }
            adapter.updateLoans(filtered)
        }
        swipeRefreshLayout = binding.swipeRefreshLoans
        swipeRefreshLayout.setOnRefreshListener {
            LoanMutableList = viewModel.getLoans().toMutableList()
            initRecyclerView()
            swipeRefreshLayout.isRefreshing = false
        }
        viewModel = LoanListViewModel(requireContext())
        LoanMutableList = viewModel.getLoans().toMutableList()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = LoanAdapter(LoanMutableList) {
            onItemSelected(it)
        }
        binding.recyclerLoan.layoutManager = manager
        binding.recyclerLoan.adapter = adapter
    }

    private fun onItemSelected(loan: Loan) {
        val bundle = bundleOf(
            NAMEARTICLE_BUNDLE to loan.articleName,
            NAMEQUANTITY_BUNDLE to loan.quantityArticle,
            NAMESTATUS_BUNDLE to "",
            NAMESTARTDATE_BUNDLE to loan.dateStart.toString(),
            NAMEENDDATE_BUNDLE to loan.dateEnd.toString(),
            NAMEISRETURNED_BUNDLE to loan.isReturned,
            NAMEMONITOR_BUNDLE to loan.monitorName,

            )
        findNavController().navigate(R.id.action_nav_loanList_to_loanFragment, bundle)
    }
}

