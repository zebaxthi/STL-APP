package com.uco.stlapp.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.uco.stlapp.databinding.ActivitySignInBinding
import com.uco.stlapp.viewModels.ArticleListViewModel
import com.uco.stlapp.viewModels.LoanListViewModel

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModelLoan: LoanListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelLoan = LoanListViewModel(this)

        binding.btSignIn.setOnClickListener{
            signIn()
        }
        binding.tvSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()
    }

    private fun signIn(){
        val email: String = binding.etEmailText.text.toString()
        val password: String = binding.etPasswordText.text.toString()

        if(!email.isNullOrEmpty() && !password.isNullOrEmpty()){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                    task ->
                    if(task.isSuccessful){
                        viewModelLoan.fetchLoansData()
                        actionSuccess()
                    }else{
                        Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun actionSuccess(){
        val intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)
    }
}