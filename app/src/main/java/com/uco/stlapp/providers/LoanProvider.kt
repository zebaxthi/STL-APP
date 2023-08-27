package com.uco.stlapp.providers

import com.uco.stlapp.models.Loan
import java.util.Calendar
import java.util.Date

class LoanProvider {


    companion object{
        val loanList = listOf<Loan>(
            Loan("Loan", "Marvel", 4, Date(2023, 8, 1), Date(2023, 8, 1), true),
            Loan("Daredevil", "Marvel", 3, Date(2023, 8, 1), Date(2023, 8, 1), true),
            Loan("Wolverine", "Marvel", 2, Date(2023, 8, 1), Date(2023, 8, 1), true),
            Loan("Batman", "DC", 4, Date(2023, 8, 1), Date(2023, 8, 1), true),
            Loan("Thor", "Marvel", 2, Date(2023, 8, 1), Date(2023, 8, 1), true),
            Loan("Flash", "DC", 5, Date(2023, 8, 1), Date(2023, 8, 1), true),
            Loan("Green Lantern", "DC", 1, Date(2023, 8, 1), Date(2023, 8, 1), true),

        )
    }
}