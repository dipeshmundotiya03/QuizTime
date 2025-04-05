package com.example.quiztime.domain.repository

import com.example.domain.util.DataError
import com.example.quiztime.domain.model.QuizQuestion
import com.example.quiztime.domain.model.QuizTopic
import domain.util.Result

interface QuizQuestionRepository {
    suspend fun getQuizQuestions(topicCode: Int): Result<List<QuizQuestion>, DataError>


}