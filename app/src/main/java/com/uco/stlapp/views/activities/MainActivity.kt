package com.uco.stlapp.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uco.stlapp.databinding.ActivityMainBinding
import com.uco.stlapp.viewModels.ArticleListViewModel
import com.uco.stlapp.viewModels.LoanListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelArticle: ArticleListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelArticle = ArticleListViewModel(this)
        viewModelArticle.fetchArticlesData()

        binding.btSingInWelcome.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.tvSignUpWelcome.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


    }
}