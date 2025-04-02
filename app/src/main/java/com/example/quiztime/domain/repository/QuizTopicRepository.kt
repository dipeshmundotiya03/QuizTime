package com.example.quiztime.domain.repository

import com.example.domain.util.DataError
import com.example.quiztime.domain.model.QuizTopic
import domain.util.Result

interface QuizTopicRepository {
    suspend fun getQuizTopics(): Result<List<QuizTopic>, DataError>
}