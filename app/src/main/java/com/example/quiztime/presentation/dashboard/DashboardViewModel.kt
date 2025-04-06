package com.example.quiztime.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiztime.domain.repository.QuizTopicRepository
import com.example.quiztime.domain.repository.UserPreferencesRepository
import com.example.quiztime.presentation.util.getErrorMessage
import domain.util.onFailure
import domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val quizTopicRepository : QuizTopicRepository,
    private val userPreferencesRepository: UserPreferencesRepository

) :ViewModel(){
    private val _state = MutableStateFlow(DashboardState())
    val state = combine(
        _state,
        userPreferencesRepository.getQuestionsAttempted(),
        userPreferencesRepository.getCorrectAnswers(),
        userPreferencesRepository.getUserName()
    ){state, questionsAttempted,correctAnswers,username->
        state.copy(
        questionAttempted = questionsAttempted,
        correctAnswers = correctAnswers,
            username = username)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )


    init {
        getQuizTopics()
    }

    fun onAction(action: DashboardAction){
        when (action){
            DashboardAction.NameEditDialogConfirm -> {
                _state.update { it.copy(isNameEditDialogOpen = false) }
                saveUsername(state.value.usernameTextFieldValue)
            }
            DashboardAction.NameEditDialogDismiss -> {
                _state.update { it.copy(isNameEditDialogOpen = false) }
            }
            DashboardAction.NameEditIconClick -> {
                _state.update { it.copy(
                    usernameTextFieldValue = state.value.username,
                    isNameEditDialogOpen = true) }
            }

            is DashboardAction.SetUserName -> {
                val usernameError = validateUserName(action.username)
                _state.update {
                    it.copy(usernameTextFieldValue = action.username,
                        usernameError = usernameError) }
            }

            DashboardAction.RefreshIconClick -> {
                getQuizTopics()
            }
        }
    }

    private fun getQuizTopics(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            quizTopicRepository.getQuizTopics()
                .onSuccess {topics->
                    _state.update {
                        it.copy(
                            quizTopics = topics,
                            errorMessage = null,
                            isLoading = false
                        ) }
                }
                .onFailure {error->
                    _state.update {
                        it.copy(
                            quizTopics = emptyList(),
                            errorMessage = error.getErrorMessage(),
                            isLoading = false
                        ) }
                }
        }
    }

    private fun saveUsername (username : String){
        viewModelScope.launch {
            val trimmedUsername = username.trim()
            userPreferencesRepository.saveUserName(trimmedUsername)
        }
    }

    private fun validateUserName(userName :String): String?{
        return when{
            userName.isBlank() -> "Username cannot be empty"
            userName.length < 3 -> "Name is too short"
            userName.length > 20 -> "Name is too long"
            else -> null

        }
    }
}