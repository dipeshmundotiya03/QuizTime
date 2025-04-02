package com.example.quiztime.presentation.result

import com.example.quiztime.domain.model.QuizQuestion
import com.example.quiztime.domain.model.UserAnswer

data class ResultState(
    val scorePercentage : Int = 0,
    val correctAnswerCount : Int = 0,
    val totalQuestionCount : Int = 0,
    val quizQuestions : List<QuizQuestion> = emptyList(),
    val userAnswers : List<UserAnswer> = emptyList()
)
