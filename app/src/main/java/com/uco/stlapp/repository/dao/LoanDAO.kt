package com.uco.stlapp.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.uco.stlapp.repository.entities.Loan

@Dao
interface LoanDAO {
    @Query("SELECT * FROM loan")
    fun getAll(): List<Loan>

    @Query("SELECT * FROM loan WHERE id = :id")
    fun findById(id: Int): Loan

    @Insert
    fun insertAll(vararg loans: Loan)

    @Update
    fun update(loan: Loan)

    @Delete
    fun delete(loan: Loan)

    @Query("DELETE FROM loan")
    fun deleteAll()

}