package com.example.quiztime.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.util.DataError
import com.example.quiztime.domain.model.UserAnswer
import com.example.quiztime.domain.repository.QuizQuestionRepository
import com.example.quiztime.presentation.quiz.component.QuizState
import com.example.quiztime.presentation.util.getErrorMessage
import domain.util.onFailure
import domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizQuestionViewModel(
    private val repository : QuizQuestionRepository
): ViewModel(

) {


    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    init {
        getQuizQuestions()
    }

    fun onAction(action: QuizAction){
         when(action){
             QuizAction.NextQuestionButtonClick -> {
                 val newIndex = (state.value.currentQuestionIndex + 1)
                     .coerceAtMost(state.value.questions.lastIndex)
                 _state.update { it.copy(currentQuestionIndex = newIndex) }
             }
             QuizAction.PrevQuestionButtonClick -> {
                 val newIndex = (state.value.currentQuestionIndex - 1)
                     .coerceAtLeast(0)
                 _state.update { it.copy(currentQuestionIndex = newIndex) }
             }

             is QuizAction.JumpToQuestion -> {
                 _state.update { it.copy(currentQuestionIndex = action.index) }
             }

             is QuizAction.OnOptionSelected -> {
                 val newAnswer = UserAnswer(action.questionId , action.answer)
                 val currentAnswers = state.value.answers.toMutableList()
                 val existingAnswerIndex = currentAnswers
                     .indexOfFirst { it.questionId == action.questionId }
                 if (existingAnswerIndex != -1){
                     currentAnswers[existingAnswerIndex] = newAnswer
                 } else{
                     currentAnswers.add(newAnswer)
                 }
                 _state.update { it.copy(answers =  currentAnswers) }
             }
         }
    }

    private fun getQuizQuestions(){
        viewModelScope.launch {
           repository.getQuizQuestions()
               .onSuccess {questions ->
                   _state.update { it.copy(
                       questions = questions,
                       errorMessage = null) }

               }
               .onFailure {error->
                   _state.update { it.copy(
                       questions = emptyList(),
                       errorMessage = error.getErrorMessage()) }
               }

        }
    }
}