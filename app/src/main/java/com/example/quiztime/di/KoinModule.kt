package com.example.quiztime.di

import com.example.quiztime.data.local.DatabaseFactory
import com.example.quiztime.data.local.QuizDatabase
import com.example.quiztime.data.remote.HttpClientFactory
import com.example.quiztime.data.remote.KtorRemoteQuizDataSource
import com.example.quiztime.data.remote.RemoteQuizDataSource
import com.example.quiztime.data.repository.IssueReportRepositoryImpl
import com.example.quiztime.data.repository.QuizQuestionRepositoryImpl
import com.example.quiztime.data.repository.QuizTopicRepositoryImpl
import com.example.quiztime.domain.repository.IssueReportRepository
import com.example.quiztime.domain.repository.QuizQuestionRepository
import com.example.quiztime.domain.repository.QuizTopicRepository
import com.example.quiztime.presentation.dashboard.DashboardViewModel
import com.example.quiztime.presentation.issue_report.IssueReportViewModel
import com.example.quiztime.presentation.quiz.QuizQuestionViewModel
import com.example.quiztime.presentation.result.ResultViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinModule = module {
    single{
        HttpClientFactory.create()
    }
    singleOf(::KtorRemoteQuizDataSource).bind<RemoteQuizDataSource>()

    single { DatabaseFactory.create(get()) }
    single { get<QuizDatabase>().quizTopicDao() }
    single { get<QuizDatabase>().quizQuestionDao() }
    single { get<QuizDatabase>().userAnswerDao() }


    singleOf(::QuizQuestionRepositoryImpl).bind<QuizQuestionRepository>()
    singleOf(::QuizTopicRepositoryImpl).bind<QuizTopicRepository>()
    singleOf(::IssueReportRepositoryImpl).bind<IssueReportRepository>()

    viewModelOf(::QuizQuestionViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::ResultViewModel)
    viewModelOf(::IssueReportViewModel)
}