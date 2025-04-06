package com.example.quiztime.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quiztime.presentation.dashboard.DashboardScreen
import com.example.quiztime.presentation.dashboard.DashboardViewModel
import com.example.quiztime.presentation.issue_report.IssueReportScreen
import com.example.quiztime.presentation.issue_report.IssueReportViewModel
import com.example.quiztime.presentation.quiz.QuizQuestionViewModel
import com.example.quiztime.presentation.quiz.QuizScreen
import com.example.quiztime.presentation.result.ResultScreen
import com.example.quiztime.presentation.result.ResultViewModel
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

        composable<Route.DashboardScreen> {
           val viewmodel = koinViewModel<DashboardViewModel>()
            val state by viewmodel.state.collectAsStateWithLifecycle()
            DashboardScreen(
                state = state,
                onAction = viewmodel::onAction,
                onTopicCardClick = {topicCode ->
                    navController.navigate(Route.QuizScreen(topicCode))
                }
            )
        }
        composable<Route.QuizScreen> {
            val viewModel = koinViewModel<QuizQuestionViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            QuizScreen(
                state = state,
                event = viewModel.event,
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
            val viewModel = koinViewModel<ResultViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            ResultScreen(
              state = state,
                event = viewModel.event,
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
            val viewModel = koinViewModel<IssueReportViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            IssueReportScreen(
                state = state,
                event = viewModel.event,
                onAction = viewModel::onAction,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}