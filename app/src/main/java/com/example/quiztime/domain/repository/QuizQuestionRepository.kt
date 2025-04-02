package com.example.quiztime.domain.repository

import com.example.domain.util.DataError
import com.example.quiztime.domain.model.QuizQuestion
import domain.util.Result

interface QuizQuestionRepository {
    suspend fun getQuizQuestions():Result< List<QuizQuestion>,DataError>
}