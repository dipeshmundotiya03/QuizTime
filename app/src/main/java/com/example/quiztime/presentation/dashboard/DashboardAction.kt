package com.example.quiztime.presentation.dashboard

sealed interface DashboardAction {
    data object NameEditIconClick : DashboardAction
    data object NameEditDialogDismiss : DashboardAction
    data object NameEditDialogConfirm : DashboardAction
    data class SetUserName(val username : String) : DashboardAction
    data object RefreshIconClick:DashboardAction
}