package com.example.quiztime.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quiztime.data.local.Entity.QuizTopicEntity
import com.example.quiztime.data.local.dao.QuizTopicDao

@Database(
    version = 1,
    entities = [QuizTopicEntity::class]
)
abstract class QuizDatabase: RoomDatabase() {

    abstract fun quizTopicDao(): QuizTopicDao
}