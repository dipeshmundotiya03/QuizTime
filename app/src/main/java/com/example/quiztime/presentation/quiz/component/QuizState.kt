package com.example.quiztime.presentation.quiz.component

import com.example.quiztime.domain.model.QuizQuestion
import com.example.quiztime.domain.model.UserAnswer

data class QuizState(
    val questions : List<QuizQuestion> = emptyList(),
    val answers : List<UserAnswer> = emptyList(),
    val currentQuestionIndex : Int =0,
    val errorMessage : String? = null,
    val isLoading : Boolean = false,
    val loadingMessage : String? = null,
    val topBarTitle : String = " Quiz",
    val isSubmitQuizDialogOpen : Boolean = false,
    val isExitQuizDialogOpen : Boolean = false
)
