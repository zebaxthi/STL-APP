package com.uco.stlapp.providers

import com.uco.stlapp.models.Loan
import java.util.Calendar
import java.util.Date

class LoanProvider {


    companion object{
        val loanList = listOf<Loan>(
            Loan("Keren", "Fuente", 1, Date(2023, 8, 1), Date(2023, 8, 1), true),
            Loan("Juan", "Multimetro", 1, Date(2023, 8, 1), Date(2023, 8, 1), true),
            Loan("Keren", "Puntas de Fuente", 2, Date(2023, 8, 1), Date(2023, 8, 1), true),
            Loan("Laura", "Router", 4, Date(2023, 8, 1), Date(2023, 8, 1), true),
            Loan("Andrea", "Arduino", 2, Date(2023, 8, 1), Date(2023, 8, 1), true),
            Loan("Juan", "Motor con Reductor", 5, Date(2023, 8, 1), Date(2023, 8, 1), true),
            Loan("Laura", "Multimetro", 1, Date(2023, 8, 1), Date(2023, 8, 1), true),

        )
    }
}