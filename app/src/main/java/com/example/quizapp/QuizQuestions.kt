package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestions : AppCompatActivity(), View.OnClickListener {

    private var currentPosition: Int = 1
    private var questionsList: ArrayList<Question>? = null
    private var selectedOption: Int = 0

    private var tvQuestion: TextView? = null
    private var imageView: ImageView? = null

    private var progressBar: ProgressBar? = null
    private var tvProgressBar: TextView? = null

    private var optionOne: TextView? = null
    private var optionTwo: TextView? = null
    private var optionThree: TextView? = null
    private var optionFour: TextView? = null

    private var btnSubmit: Button? = null

    private var user_name: String? = null

    private var correctAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        tvQuestion = findViewById(R.id.tv_question)
        imageView = findViewById(R.id.iv_image)

        progressBar = findViewById(R.id.progressBar)
        tvProgressBar = findViewById(R.id.tv_progress)

        optionOne = findViewById(R.id.optionOne)
        optionTwo = findViewById(R.id.optionTwo)
        optionThree = findViewById(R.id.optionThree)
        optionFour = findViewById(R.id.optionFour)
        btnSubmit =findViewById(R.id.btn_submit)

        user_name = intent.getStringExtra(Constants.USER_NAME)

        questionsList = Constants.getQuestions()
        progressBar?.max = questionsList!!.size

        setQuestion()

        optionOne?.setOnClickListener(this)
        optionTwo?.setOnClickListener(this)
        optionThree?.setOnClickListener(this)
        optionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
    }

    private fun setQuestion() {
        defaultOptionView()
        var question: Question = questionsList!![currentPosition - 1]

        tvQuestion?.text = question.question
        imageView?.setImageResource(question.image)

        progressBar?.progress = currentPosition
        tvProgressBar?.text = "$currentPosition/${progressBar?.max}"

        optionOne?.text = question.optionOne
        optionTwo?.text = question.optionTwo
        optionThree?.text = question.optionThree
        optionFour?.text = question.optionFour

        if(currentPosition == questionsList!!.size){
            btnSubmit?.text = "Finish"
        }else{
            btnSubmit?.text = "Submit"
        }

//        defaultOptionView()
    }

    private fun defaultOptionView(){
        val options = ArrayList<TextView>()

        optionOne?.let {
            options.add(0, it)
        }
        optionTwo?.let {
            options.add(1, it)
        }
        optionThree?.let {
            options.add(2, it)
        }
        optionFour?.let {
            options.add(3, it)
        }

        for (option in options){
            option.setTextColor(Color.parseColor("#27303F"))
//            option.setTextColor(Color.parseColor("#EE6C4D"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.options_background)
//            option.background = ContextCompat.getDrawable(this, R.drawable.selected_option_background)
        }
    }

    private fun selectedOption(tv: TextView, selected: Int){
        defaultOptionView()
        selectedOption = selected
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_background)
        tv.setTextColor(Color.parseColor("#662D9F"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
    }

    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {

            1 -> {
                optionOne?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                optionTwo?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                optionThree?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                optionFour?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }


    override fun onClick(view: View?) {
        when(view?.id){
            R.id.optionOne -> {
                optionOne?.let {
                    selectedOption(it, 1)
                }
            }

            R.id.optionTwo -> {
                optionTwo?.let {
                    selectedOption(it, 2)
                }
            }

            R.id.optionThree -> {
                optionThree?.let {
                    selectedOption(it, 3)
                }
            }

            R.id.optionFour -> {
                optionFour?.let {
                    selectedOption(it, 4)
                }
            }

            R.id.btn_submit ->{
                if (selectedOption == 0) {

                    currentPosition++

                    when {

                        currentPosition <= questionsList!!.size -> {

                            setQuestion()
                        }
                        else -> {

//                            Toast.makeText(this, "You have successfully completed the quiz.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, Result::class.java)
                            intent.putExtra(Constants.USER_NAME, user_name)
                            intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, questionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = questionsList?.get(currentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAnswer != selectedOption) {
                        answerView(selectedOption, R.drawable.wrong_option_background)
                    }else{
                        correctAnswers++
                    }

                    // This is for correct answer
                    answerView(question.correctAnswer, R.drawable.correct_option_background)

                    if (currentPosition == questionsList!!.size) {
                        btnSubmit?.text = "FINISH"
                    } else {
                        btnSubmit?.text = "GO TO NEXT QUESTION"
                    }

                    selectedOption = 0
                }

            }
        }
    }
}