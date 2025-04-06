package com.example.quiztime.data.local.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quiztime.data.util.Constant.USER_ANSWER_TABLE_NAME

@Entity(tableName = USER_ANSWER_TABLE_NAME)
data class UserAnswerEntity(
    @PrimaryKey
    val questionId: String,
    val selectedOption: String

)
