package com.uco.stlapp.models

import com.uco.stlapp.repository.entities.User
import java.util.Date

data class Loan(
    val id: String?,
    val idMonitor: Int?,
    val monitorName: String?,
    val idArticle: Int,
    val articleName: String,
    val idUser: String,
    val quantityArticle: Int,
    val dateStart: Date,
    val dateEnd: Date?,
    val isReturned: Boolean,
)
fun com.uco.stlapp.repository.entities.Loan.toModel(): Loan {
    return Loan (this.id, this.idMonitor, this.monitorName, this.idArticle, this.articleName, this.idUser, this.quantityArticle, this.dateStart, this.dateEnd, this.isReturned)
}