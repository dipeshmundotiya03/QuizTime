package com.example.quiztime.data.remote

import com.example.domain.util.DataError
import com.example.quiztime.data.remote.dto.IssueReportDto
import com.example.quiztime.data.remote.dto.QuizQuestionDto
import com.example.quiztime.data.remote.dto.QuizTopicDto
import domain.util.Result

interface RemoteQuizDataSource {
    suspend fun getQuizTopics(): Result<List<QuizTopicDto>,DataError>
    suspend fun getQuizQuestions(topicCode: Int): Result<List<QuizQuestionDto>,DataError>
    suspend fun insertIssueReport(issueReport: IssueReportDto): Result<Unit,DataError>
}