package com.example.quizgame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizgame.databinding.ActivityLoginPageBinding
import com.google.firebase.auth.FirebaseAuth

class loginPage : AppCompatActivity() {
    lateinit var loginPageBinding: ActivityLoginPageBinding
   val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       loginPageBinding = ActivityLoginPageBinding.inflate(layoutInflater)
        val view = loginPageBinding.root
        setContentView(view)
        loginPageBinding.buttonSignIn.setOnClickListener(){
          val userEmail = loginPageBinding.emailLogIn.text.toString()
            val userPassword = loginPageBinding.passLogIn.text.toString()
            signInWithFirebase(userEmail,userPassword)
        }
       loginPageBinding.buttonGoogle.setOnClickListener(){

       }
        loginPageBinding.textViewSignUp.setOnClickListener(){
              val intent = Intent(this@loginPage,SignUpPage::class.java)
            startActivity(intent)
            finish()
        }
        loginPageBinding.textViewPassword.setOnClickListener(){

        }
    }
    fun signInWithFirebase(userEmail:String, userPassword:String){
        auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(applicationContext,"Welcome to the Quiz",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@loginPage,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user!=null){
            Toast.makeText(applicationContext,"Welcome to the Quiz",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@loginPage,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}