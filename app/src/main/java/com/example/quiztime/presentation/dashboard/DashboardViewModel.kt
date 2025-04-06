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
        userPreferencesRepository.getCorrectAnswers()
    ){state, questionsAttempted,correctAnswers->
        state.copy(
        questionAttempted = questionsAttempted,
        correctAnswers = correctAnswers )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )


    init {
        getQuizTopics()
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
}