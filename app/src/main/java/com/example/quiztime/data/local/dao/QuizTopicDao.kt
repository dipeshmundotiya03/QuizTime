package com.example.quiztime.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quiztime.data.local.Entity.QuizTopicEntity

@Dao
interface QuizTopicDao {

    @Query("SELECT * FROM quiz_topics")
    suspend fun getAllQuizTopics(): List<QuizTopicEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizTopics(topics: List<QuizTopicEntity>)

    @Query("DELETE FROM quiz_topics")
    suspend fun clearAllQuizTopics()

    @Query("SELECT * FROM quiz_topics WHERE code  = :topicCode")
    suspend fun getQuizTopicByCode(topicCode: Int): QuizTopicEntity

}