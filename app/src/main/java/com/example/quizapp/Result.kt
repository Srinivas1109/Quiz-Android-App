package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Result : AppCompatActivity() {
    private var tv_username: TextView? = null
    private var tv_result: TextView? = null
    private var btn_finish: Button? = null
    private var correctAnswers: Int = 0
    private var totalQuestions: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        tv_username = findViewById(R.id.tv_username)
        tv_result = findViewById(R.id.tv_result)
        btn_finish = findViewById(R.id.btn_finish)

        tv_username?.text = intent.getStringExtra(Constants.USER_NAME)

        correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)

        tv_result?.text = "Your score is $correctAnswers out of $totalQuestions"

        btn_finish?.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}