package com.example.quiztime.di

import com.example.quiztime.data.local.DatabaseFactory
import com.example.quiztime.data.local.QuizDatabase
import com.example.quiztime.data.remote.HttpClientFactory
import com.example.quiztime.data.remote.KtorRemoteQuizDataSource
import com.example.quiztime.data.remote.RemoteQuizDataSource
import com.example.quiztime.data.repository.QuizQuestionRepositoryImpl
import com.example.quiztime.data.repository.QuizTopicRepositoryImpl
import com.example.quiztime.domain.repository.QuizQuestionRepository
import com.example.quiztime.domain.repository.QuizTopicRepository
import com.example.quiztime.presentation.dashboard.DashboardViewModel
import com.example.quiztime.presentation.quiz.QuizQuestionViewModel
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
    singleOf(::QuizQuestionRepositoryImpl).bind<QuizQuestionRepository>()
    singleOf(::QuizTopicRepositoryImpl).bind<QuizTopicRepository>()


    viewModelOf(::QuizQuestionViewModel)
    viewModelOf(::DashboardViewModel)
}