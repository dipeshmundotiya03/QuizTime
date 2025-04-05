package com.example.quiztime.data.repository

import android.annotation.SuppressLint
import com.example.domain.util.DataError
import com.example.quiztime.data.mapper.toQuizQuestions
import com.example.quiztime.data.remote.RemoteQuizDataSource
import com.example.quiztime.domain.model.QuizQuestion
import com.example.quiztime.domain.model.QuizTopic
import com.example.quiztime.domain.repository.QuizQuestionRepository
import domain.util.Result

class QuizQuestionRepositoryImpl(
    private val remoteDataSource: RemoteQuizDataSource
) : QuizQuestionRepository {

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getQuizQuestions(topicCode: Int): Result<List<QuizQuestion>, DataError> {
        return when (val result = remoteDataSource.getQuizQuestions(topicCode)) {
            is Result.Success -> {
                val questionDto = result.data
                Result.Success(questionDto.toQuizQuestions())
            }

            is Result.Failure -> result
        }
    }

}