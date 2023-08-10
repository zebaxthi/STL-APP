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
