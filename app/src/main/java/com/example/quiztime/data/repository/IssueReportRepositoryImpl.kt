package com.example.quiztime.data.repository

import com.example.domain.util.DataError
import com.example.quiztime.data.mapper.toIssueReportDto
import com.example.quiztime.data.remote.RemoteQuizDataSource
import com.example.quiztime.domain.model.IssueReport
import com.example.quiztime.domain.repository.IssueReportRepository
import domain.util.Result

class IssueReportRepositoryImpl (
    private val remoteDataSource: RemoteQuizDataSource
): IssueReportRepository{
    override suspend fun insertIssueReport(issueReport: IssueReport): Result<Unit, DataError> {
        val reportDto = issueReport.toIssueReportDto()
        return remoteDataSource.insertIssueReport(reportDto)
    }

}