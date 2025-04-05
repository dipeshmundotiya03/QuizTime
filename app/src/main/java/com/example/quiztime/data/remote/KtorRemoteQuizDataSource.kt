package com.example.quiztime.data.remote

import com.example.domain.util.DataError
import com.example.quiztime.data.remote.dto.QuizQuestionDto
import com.example.quiztime.data.remote.dto.QuizTopicDto
import com.example.quiztime.data.util.Constant.BASE_URL
import domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter


class KtorRemoteQuizDataSource(
    private val httpClient: HttpClient
):RemoteQuizDataSource {

    override suspend fun getQuizTopics(): Result<List<QuizTopicDto>, DataError> {
        return safeCall<List<QuizTopicDto>> {
            httpClient.get(urlString = "$BASE_URL/quiz/topics")
        }

    }


    override suspend fun getQuizQuestions(topicCode: Int): Result<List<QuizQuestionDto>, DataError> {
        return safeCall<List<QuizQuestionDto>> {
            httpClient.get(urlString = "$BASE_URL/quiz/question/random"){
                parameter("topicCode",topicCode)
            }
        }
    }
}