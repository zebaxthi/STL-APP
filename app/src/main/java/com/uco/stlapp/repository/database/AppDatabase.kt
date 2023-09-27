package com.uco.stlapp.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.uco.stlapp.repository.dao.ArticleDAO
import com.uco.stlapp.repository.dao.LoanDAO
import com.uco.stlapp.repository.dao.UserDao
import com.uco.stlapp.repository.entities.Article
import com.uco.stlapp.repository.entities.Loan
import com.uco.stlapp.repository.entities.User

@Database(entities = [Loan::class, Article::class, User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "sltmovil.db"

        private lateinit var instance: AppDatabase

        fun getInstance(context: Context?): AppDatabase {
            return if (::instance.isInitialized) {
                instance
            } else {
                instance = Room.databaseBuilder(
                    context!!.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()

                instance
            }
        }
    }

    abstract fun articleDAO(): ArticleDAO
    abstract fun loanDAO(): LoanDAO
    abstract fun userDAO(): UserDao

}