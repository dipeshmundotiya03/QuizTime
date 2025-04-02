package com.example.quiztime.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiztime.domain.repository.QuizTopicRepository
import com.example.quiztime.presentation.util.getErrorMessage
import domain.util.onFailure
import domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val quizTopicRepository : QuizTopicRepository

) :ViewModel(){
    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()


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