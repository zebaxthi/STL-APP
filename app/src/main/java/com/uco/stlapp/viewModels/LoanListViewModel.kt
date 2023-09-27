package com.uco.stlapp.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.uco.stlapp.models.Article
import com.uco.stlapp.models.Loan
import com.uco.stlapp.models.toModel
import com.uco.stlapp.repository.dao.ArticleDAO
import com.uco.stlapp.repository.dao.LoanDAO
import com.uco.stlapp.repository.database.AppDatabase

class LoanListViewModel(context: Context)  : ViewModel() {
    private var loanDao: LoanDAO =  AppDatabase.getInstance(context).loanDAO()
    fun getLoans(): List<Loan> {
        val entities = loanDao.getAll()
        return entities.map { it.toModel() }
    }
}