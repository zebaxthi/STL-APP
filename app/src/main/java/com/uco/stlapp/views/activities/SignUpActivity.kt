package com.uco.stlapp.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.uco.stlapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var dbReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("User")

        binding.btSignUp.setOnClickListener{
            createNewAccount()
        }

        binding.tvSignIn.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createNewAccount(){
        val name: String = binding.etNameText.text.toString()
        val lastname: String = binding.etLastnameText.text.toString()
        val email: String = binding.etEmailTextUp.text.toString()
        val password: String = binding.etPasswordText.text.toString()
        val passwordC: String = binding.etpasswordCText.text.toString()
        val numberPhone: String = binding.etPhoneText.text.toString()
        if(!name.isNullOrEmpty() && !lastname.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty() && !passwordC.isNullOrEmpty() && !numberPhone.isNullOrEmpty()){
            if(password != passwordC){
                Toast.makeText(this, "la contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }else{
                val dominio = email.split("@").last()
                if(dominio == "uco.net.co" || dominio == "soyuco.edu.co"){
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this){
                                task ->
                            if(task.isComplete){
                                val user: FirebaseUser?=auth.currentUser
                                verifyEmail(user)
                                val userDB = dbReference.child(user?.uid.toString())
                                userDB.child("name").setValue(name)
                                userDB.child("lastname").setValue(lastname)
                                userDB.child("numberPhone").setValue(numberPhone)
                                action()
                            }
                        }
                }else{
                    Toast.makeText(this, "tienes que usar correo institucional", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun action(){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun verifyEmail(user: FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                task ->
                if(task.isComplete){
                    Toast.makeText(this, "Correo de verificación enviado", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Error al enviar el correo", Toast.LENGTH_SHORT).show()
                }
            }

    }
}