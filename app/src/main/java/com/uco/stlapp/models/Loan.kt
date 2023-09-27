package com.uco.stlapp.models

import java.util.Date

data class Loan (
    val monitorName: String,
    val articleName: String,
    val quantityArticle: Int,
    val dateStart: Date,
    val dateEnd: Date,
    val isReturned: Boolean,
)
fun com.uco.stlapp.repository.entities.Loan.toModel(): Loan {
    return Loan (this.monitorName, this.articleName, this.quantityArticle, this.dateStart, this.dateEnd, this.isReturned)
}