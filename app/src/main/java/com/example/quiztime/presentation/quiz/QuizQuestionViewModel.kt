package com.example.quiztime.presentation.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.quiztime.domain.model.UserAnswer
import com.example.quiztime.domain.repository.QuizQuestionRepository
import com.example.quiztime.domain.repository.QuizTopicRepository
import com.example.quiztime.presentation.navigation.Route
import com.example.quiztime.presentation.quiz.component.QuizState
import com.example.quiztime.presentation.util.getErrorMessage
import domain.util.onFailure
import domain.util.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizQuestionViewModel(
    private val repository : QuizQuestionRepository,
    savedStateHandle: SavedStateHandle,
    private val topicRepository: QuizTopicRepository,
    private val questionRepository: QuizQuestionRepository
): ViewModel(

) {
  private  val topicCode = savedStateHandle.toRoute<Route.QuizScreen>().topicCode

    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    private val _event = Channel<QuizEvent>()
    val event = _event.receiveAsFlow()

    init {
       setupQuiz()
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

             QuizAction.ExitQuizButtonClick -> {
                 _state.update { it.copy(isExitQuizDialogOpen = true) }
             }

             QuizAction.ExitQuizDialogDismiss -> {
                 _state.update { it.copy(isExitQuizDialogOpen = false) }
             }
             QuizAction.ExitQuizConfirmButtonClick -> {
             _state.update { it.copy(isExitQuizDialogOpen = false) }
             _event.trySend(QuizEvent.NavigateToDashboardScreen)
             }
             QuizAction.SubmitQuizConfirmButtonClick -> {
                 _state.update { it.copy(isSubmitQuizDialogOpen = false) }
                 saveUserAnswers()
             }
             QuizAction.SubmitQuizButtonClick -> {
                 _state.update { it.copy(isSubmitQuizDialogOpen = true) }
             }
             QuizAction.SubmitQuizDialogDismiss -> {
                 _state.update { it.copy(isSubmitQuizDialogOpen = false) }
             }

             QuizAction.Refresh -> {
                 setupQuiz()
             }
         }
    }
    private fun setupQuiz(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    loadingMessage = "Setting up Quiz ....")
            }
            getQuizTopicName(topicCode)
            getQuizQuestions(topicCode)
            _state.update {
                it.copy(isLoading = false, loadingMessage = null)
            }
        }
    }

    private suspend fun getQuizQuestions(topicCode : Int){

           repository.fetchAndSaveQuizQuestions(topicCode)
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


    private suspend fun getQuizTopicName(topicCode :Int){

            topicRepository.getQuizTopicByCode(topicCode)
                .onSuccess {topic->
                    _state.update { it.copy(
                        topBarTitle = topic.name +" Quiz"
                    ) }
                }
                .onFailure {error->
                    _event.send(QuizEvent.ShowToast(error.getErrorMessage()))
                }


    }

    private fun saveUserAnswers(){
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, loadingMessage = "Submitting Quiz ....")
            }
            questionRepository.saveUserAnswers(state.value.answers)
                .onFailure { error->
                    _event.send(QuizEvent.ShowToast(error.getErrorMessage()))
                }
            _state.update {
                it.copy(isLoading = false, loadingMessage = "Submitting Quiz ....")
            }
            _event.send(QuizEvent.NavigateToResultScreen)
        }
    }
}