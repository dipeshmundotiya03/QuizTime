package com.example.quiztime.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getQuestionsAttempted(): Flow<Int>

    fun getCorrectAnswers(): Flow<Int>

    suspend fun saveScore(questionsAttempted: Int, correctAnswers: Int)
}