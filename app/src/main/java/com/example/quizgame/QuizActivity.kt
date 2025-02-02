package com.example.quizgame

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizgame.databinding.ActivityQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class QuizActivity : AppCompatActivity() {
    lateinit var quizBinding: ActivityQuizBinding
    val database = FirebaseDatabase.getInstance()
    val databaseReference = database.reference.child("questions")
    var question = ""
    var answerA = ""
    var answerB = ""
    var answerC = ""
    var answerD = ""
    var correctAnswer = ""
    var questionCount = 0
    var questionNumber = 1
    var userAnswer = ""
    var userCorrect = 0
    var userWrong = 0
    lateinit var timer: CountDownTimer
    private val totalTime = 30000L
    var timeContinue = false
    var leftTime = totalTime
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val scoreRef = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizBinding = ActivityQuizBinding.inflate(layoutInflater)
        val view = quizBinding.root
        setContentView(view)
        gameLogic()
        quizBinding.btnNext.setOnClickListener() {
            resetTimer()
            gameLogic()
        }
        quizBinding.btnFinish.setOnClickListener() {
            sendScore()
        }
        quizBinding.option1.setOnClickListener() {
            pauseTime()
            userAnswer = "a"
            if (correctAnswer == userAnswer) {
                quizBinding.option1.setBackgroundColor(Color.GREEN)
                userCorrect++
                quizBinding.Correct.text = userCorrect.toString()
            } else {
                quizBinding.option1.setBackgroundColor(Color.RED)
                userWrong++
                quizBinding.wrong.text = userWrong.toString()
                findAnswer()
            }
            disableClick()
        }
        quizBinding.option2.setOnClickListener() {
            pauseTime()
            userAnswer = "b"
            if (correctAnswer == userAnswer) {
                quizBinding.option2.setBackgroundColor(Color.GREEN)
                userCorrect++
                quizBinding.Correct.text = userCorrect.toString()
            } else {
                quizBinding.option2.setBackgroundColor(Color.RED)
                userWrong++
                quizBinding.wrong.text = userWrong.toString()
                findAnswer()
            }
            disableClick()
        }
        quizBinding.option3.setOnClickListener() {
            pauseTime()
            userAnswer = "c"
            if (correctAnswer == userAnswer) {
                quizBinding.option3.setBackgroundColor(Color.GREEN)
                userCorrect++
                quizBinding.Correct.text = userCorrect.toString()
            } else {
                quizBinding.option3.setBackgroundColor(Color.RED)
                userWrong++
                quizBinding.wrong.text = userWrong.toString()
                findAnswer()
            }
            disableClick()
        }
        quizBinding.option4.setOnClickListener() {
            pauseTime()
            userAnswer = "d"
            if (correctAnswer == userAnswer) {
                quizBinding.option4.setBackgroundColor(Color.GREEN)
                userCorrect++
                quizBinding.Correct.text = userCorrect.toString()
            } else {
                quizBinding.option4.setBackgroundColor(Color.RED)
                userWrong++
                quizBinding.wrong.text = userWrong.toString()
                findAnswer()
            }
            disableClick()
        }


    }

    private fun gameLogic() {
        restoreOptions()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                questionCount = snapshot.childrenCount.toInt()
                if (questionNumber <= questionCount) {
                    question = snapshot.child(questionNumber.toString()).child("q").value.toString()
                    answerA = snapshot.child(questionNumber.toString()).child("a").value.toString()
                    answerB = snapshot.child(questionNumber.toString()).child("b").value.toString()
                    answerC = snapshot.child(questionNumber.toString()).child("c").value.toString()
                    answerD = snapshot.child(questionNumber.toString()).child("d").value.toString()
                    correctAnswer =
                        snapshot.child(questionNumber.toString()).child("answer").value.toString()

                    quizBinding.textViewQuestion.text = question
                    quizBinding.option1.text = answerA
                    quizBinding.option2.text = answerB
                    quizBinding.option3.text = answerC
                    quizBinding.option4.text = answerD

                    quizBinding.progressBarQuiz.visibility = View.INVISIBLE
                    quizBinding.linearLayoutInfo.visibility = View.VISIBLE
                    quizBinding.linearLayoutQuestion.visibility = View.VISIBLE
                    quizBinding.linearLayoutoptions.visibility = View.VISIBLE
                    quizBinding.linearLayoutButton.visibility = View.VISIBLE
                    startTimer()

                } else {
                    val dialogMessage = AlertDialog.Builder(this@QuizActivity)
                    dialogMessage.setTitle("Quiz Game")
                    dialogMessage.setMessage("Congratulations!\nYou have answered all the questions.Do you want to see the  result?")
                    dialogMessage.setCancelable(false)
                    dialogMessage.setPositiveButton("See result"){dialogWindow,position->
                     sendScore()
                    }
                    dialogMessage.setNegativeButton("Play again"){dialogWindow, position->
                        val intent = Intent(this@QuizActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    dialogMessage.create().show()

                }
                questionNumber++

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun findAnswer() {
        when (correctAnswer) {
            "a" -> quizBinding.option1.setBackgroundColor(Color.GREEN)
            "b" -> quizBinding.option2.setBackgroundColor(Color.GREEN)
            "c" -> quizBinding.option3.setBackgroundColor(Color.GREEN)
            "d" -> quizBinding.option4.setBackgroundColor(Color.GREEN)
        }
    }

    fun disableClick() {
        quizBinding.option1.isClickable = false
        quizBinding.option2.isClickable = false
        quizBinding.option3.isClickable = false
        quizBinding.option4.isClickable = false
    }

    fun restoreOptions() {
        quizBinding.option1.setBackgroundColor(Color.WHITE)
        quizBinding.option2.setBackgroundColor(Color.WHITE)
        quizBinding.option3.setBackgroundColor(Color.WHITE)
        quizBinding.option4.setBackgroundColor(Color.WHITE)

        quizBinding.option1.isClickable = true
        quizBinding.option2.isClickable = true
        quizBinding.option3.isClickable = true
        quizBinding.option4.isClickable = true
    }

    private fun startTimer() {
        timer = object : CountDownTimer(leftTime, 1000) {
            override fun onTick(millisUntilFinish: Long) {
                leftTime = millisUntilFinish
                updateCountDownText()
            }

            override fun onFinish() {
                disableClick()
                resetTimer()
                updateCountDownText()
                quizBinding.textViewQuestion.text = "Sorry, Time is up!Continue with next question."
                timeContinue = false
            }

        }.start()
        timeContinue = true
    }

    fun updateCountDownText() {
        val remainingTime: Int = (leftTime / 1000).toInt()
        quizBinding.tvSeconds.text = remainingTime.toString()
    }

    fun pauseTime() {
        timer.cancel()
        timeContinue = false
    }

    fun resetTimer() {
        pauseTime()
        leftTime = totalTime
        updateCountDownText()
    }

    fun sendScore() {
        user?.let {
            val userUID = it.uid
            scoreRef.child("scores").child(userUID).child("correct").setValue(userCorrect)
            scoreRef.child("scores").child(userUID).child("wrong").setValue(userWrong)
                .addOnSuccessListener {
                    Toast.makeText(this, "Score saved successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@QuizActivity, ResultActivity::class.java)
                    startActivity(intent)
                    finish()
                }
        }
    }
}
