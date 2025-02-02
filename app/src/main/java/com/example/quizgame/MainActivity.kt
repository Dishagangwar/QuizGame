package com.example.quizgame

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizgame.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        mainBinding.btnStart.setOnClickListener(){
            val intent = Intent(this@MainActivity,QuizActivity::class.java)
            startActivity(intent)
            finish()
        }
        mainBinding.btmSignOut.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@MainActivity,loginPage::class.java)
            startActivity(intent)
            finish()
        }

    }
}