package com.uco.stlapp.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uco.stlapp.models.Loan
import com.uco.stlapp.models.toModel
import com.uco.stlapp.repository.dao.LoanDAO
import com.uco.stlapp.repository.database.AppDatabase
import java.security.Timestamp
import java.util.Date
class LoanListViewModel(context: Context)  : ViewModel() {
    private var loanDao: LoanDAO =  AppDatabase.getInstance(context).loanDAO()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db = Firebase.firestore

    fun getLoans(): List<Loan> {
        val entities = loanDao.getAll()
        return entities.map { it.toModel() }
    }

    fun fetchLoansData(){
        var user = auth.currentUser
        db.collection("loans")
            .whereEqualTo("idUser", user?.uid.toString())
            .get()
            .addOnSuccessListener { result ->
                loanDao.deleteAll()
                val loans = result.map { document ->
                    val id = document.id
                    val data = document.data

                    val idMonitor = (data["idMonitor"] as? Long)?.toInt() ?: 0
                    val monitorName = if (data["monitorName"] == null) "" else data["monitorName"].toString()
                    val idArticle = (data["idArticle"] as? Long)?.toInt() ?: 0
                    val userId = data["idUser"].toString()
                    val articleName = data["articleName"].toString()
                    val quantityArticle = (data["quantityArticle"] as? Long)?.toInt() ?: 0
                    val timestampDateStart = data["dateStart"] as? com.google.firebase.Timestamp
                    val dateStart: Date? = timestampDateStart?.toDate()
                    val timestampDateEnd = data["dateEnd"] as? com.google.firebase.Timestamp
                    val dateEnd = timestampDateEnd?.toDate()
                    val isReturned = data["returned"] as? Boolean ?: false

                    val loan = com.uco.stlapp.repository.entities.Loan(id, idMonitor, monitorName, idArticle, userId, articleName, quantityArticle, dateStart!!, dateEnd!!, isReturned)
                    loan
                }
                loans.forEach { loan ->
                    loanDao.insertAll(loan)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}