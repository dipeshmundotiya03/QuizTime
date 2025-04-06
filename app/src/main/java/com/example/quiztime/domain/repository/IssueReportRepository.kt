package com.example.quiztime.domain.repository


import com.example.domain.util.DataError
import com.example.quiztime.domain.model.IssueReport
import domain.util.Result

interface IssueReportRepository {

    suspend fun insertIssueReport(issueReport: IssueReport): Result<Unit,DataError>
}