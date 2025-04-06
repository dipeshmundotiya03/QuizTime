package com.example.quiztime.data.mapper

import com.example.quiztime.data.remote.dto.IssueReportDto
import com.example.quiztime.data.util.toFormattedDateTimeString
import com.example.quiztime.domain.model.IssueReport

fun IssueReport.toIssueReportDto()= IssueReportDto(
    questionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timestamp = timeStampMillis.toFormattedDateTimeString()
)