package com.example.quiztime.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.quiztime.domain.model.QuizQuestion
import com.example.quiztime.presentation.dashboard.DashboardScreen
import com.example.quiztime.presentation.dashboard.DashboardViewModel
import com.example.quiztime.presentation.issue_report.IssueReportScreen
import com.example.quiztime.presentation.issue_report.IssueReportState
import com.example.quiztime.presentation.quiz.QuizQuestionViewModel
import com.example.quiztime.presentation.quiz.QuizScreen
import com.example.quiztime.presentation.quiz.component.QuizState
import com.example.quiztime.presentation.result.ResultScreen
import com.example.quiztime.presentation.result.ResultState
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
){
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = Route.DashboardScreen
    ){
        val dummyQuestions = List(size = 10){index->
            QuizQuestion(
                id = "$index",
                topicCode = 1,
                question = "What is the language for android Dev?",
                allOptions = listOf("Java","Kotlin","C++","Python"),
                correctAnswer = "Kotlin",
                explanation = "Kotlin is a programming language"
            )
        }

        composable<Route.DashboardScreen> {
           val viewmodel = koinViewModel<DashboardViewModel>()
            val state by viewmodel.state.collectAsStateWithLifecycle()
            DashboardScreen(
                state = state,
                onTopicCardClick = {topicCode ->
                    navController.navigate(Route.QuizScreen(topicCode))
                }
            )
        }
        composable<Route.QuizScreen> {
           val topicCode = it.toRoute<Route.QuizScreen>().topicCode
            val viewModel = koinViewModel<QuizQuestionViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            QuizScreen(
                state = state,
                onAction = viewModel::onAction,
                    navigateToDashboard = {
                        navController.navigateUp()
                    },
                navigateToResult = {
                    navController.navigate(Route.ResultScreen){
                        popUpTo<Route.QuizScreen>{
                            inclusive = true
                        }
                    }
                }
            )

        }
        composable<Route.ResultScreen> {
            ResultScreen(
                state = ResultState(quizQuestions = dummyQuestions),
                onReportClick = {questionId->
                    navController.navigate(Route.IssueReportScreen(questionId))
                },
                onStartNewQuizClick = {
                    navController.navigate(Route.DashboardScreen){
                        popUpTo<Route.ResultScreen>{
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Route.IssueReportScreen> {
            IssueReportScreen(
                state = IssueReportState(
                    quizQuestion = dummyQuestions[0]
                ),
                onBackButtonClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}