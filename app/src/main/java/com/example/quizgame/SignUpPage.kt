package com.example.quizgame

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizgame.databinding.ActivitySignUpPageBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpPage : AppCompatActivity() {
    lateinit var signUpPageBinding: ActivitySignUpPageBinding
    val auth : FirebaseAuth =FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         signUpPageBinding = ActivitySignUpPageBinding.inflate(layoutInflater)
        val view = signUpPageBinding.root
        setContentView(view)
        signUpPageBinding.btnsignUp.setOnClickListener(){
            val email = signUpPageBinding.emailsignIn.text.toString()
            val password= signUpPageBinding.passSignIn.text.toString()
            signUpWithFirebase(email,password)
        }
    }
    fun signUpWithFirebase(email:String, password:String){
        signUpPageBinding.progressBarSignUp.visibility = View.VISIBLE
        signUpPageBinding.btnsignUp.isClickable = false
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(applicationContext,"Your account has been created",Toast.LENGTH_SHORT).show()
                finish()
                signUpPageBinding.progressBarSignUp.visibility = View.INVISIBLE
                signUpPageBinding.btnsignUp.isClickable = true
            }
            else{
                Toast.makeText(applicationContext, task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }
    }
}