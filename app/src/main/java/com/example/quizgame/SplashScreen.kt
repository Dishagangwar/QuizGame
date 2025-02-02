package com.example.quizgame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.quizgame.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var splashBinding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         splashBinding= ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = splashBinding.root
        setContentView(view)
        val alphaAnimation = android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.anim.anim)
        splashBinding.tvQuizGame.startAnimation(alphaAnimation)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object :Runnable{
            override fun run() {
                val intent = Intent(this@SplashScreen,loginPage::class.java)
                startActivity(intent)
                finish()
            }

        },5000)
    }
}