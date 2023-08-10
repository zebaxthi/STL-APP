package com.uco.stlapp.providers

import com.uco.stlapp.models.Article
import com.uco.stlapp.models.Loan
import java.util.Calendar

class LoanProvider {
    companion object{
        val loanList = listOf<Loan>(
            Loan("Spiderman", "Marvel", 4, Calendar.getInstance().time, Calendar.getInstance().time, true),
            Loan("Daredevil", "Marvel", 3, Calendar.getInstance().time, Calendar.getInstance().time, true),
            Loan("Wolverine", "Marvel", 2, Calendar.getInstance().time, Calendar.getInstance().time, true),
            Loan("Batman", "DC", 4, Calendar.getInstance().time, Calendar.getInstance().time, true),
            Loan("Thor", "Marvel", 2, Calendar.getInstance().time, Calendar.getInstance().time, true),
            Loan("Flash", "DC", 5, Calendar.getInstance().time, Calendar.getInstance().time, true),
            Loan("Green Lantern", "DC", 1, Calendar.getInstance().time, Calendar.getInstance().time, true),

        )
    }
}