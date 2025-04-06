package com.example.quiztime.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quiztime.data.local.Entity.QuizQuestionEntity
import com.example.quiztime.data.local.Entity.QuizTopicEntity
import com.example.quiztime.data.local.Entity.UserAnswerEntity
import com.example.quiztime.data.local.converter.OptionListConverters
import com.example.quiztime.data.local.dao.QuizQuestionDao
import com.example.quiztime.data.local.dao.QuizTopicDao
import com.example.quiztime.data.local.dao.UserAnswerDao

@Database(
    version = 3,
    entities = [
        QuizTopicEntity::class,
    QuizQuestionEntity::class,
    UserAnswerEntity::class
    ]
)
@TypeConverters(
    OptionListConverters::class
)
abstract class QuizDatabase: RoomDatabase() {

    abstract fun quizTopicDao(): QuizTopicDao

    abstract fun quizQuestionDao(): QuizQuestionDao

    abstract fun userAnswerDao(): UserAnswerDao
}