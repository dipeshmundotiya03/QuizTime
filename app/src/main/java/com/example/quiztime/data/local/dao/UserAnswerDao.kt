package com.example.quiztime.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quiztime.data.local.Entity.UserAnswerEntity


@Dao
interface UserAnswerDao {

    @Query("SELECT * FROM user_answers")
    suspend fun getAllUserAnswers(): List<UserAnswerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAnswer(userAnswer: List<UserAnswerEntity>)

    @Query("DELETE FROM user_answers")
    suspend fun clearAllUserAnswers()

}