package com.example.quiztime.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getQuestionsAttempted(): Flow<Int>

    fun getCorrectAnswers(): Flow<Int>

    fun getUserName(): Flow<String>

    suspend fun saveScore(questionsAttempted: Int, correctAnswers: Int)

    suspend fun saveUserName(name : String)
}