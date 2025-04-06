package com.example.quiztime.data.mapper

import com.example.quiztime.data.local.Entity.QuizQuestionEntity
import com.example.quiztime.data.remote.dto.QuizQuestionDto

import com.example.quiztime.domain.model.QuizQuestion

private fun QuizQuestionDto.toQuizQuestions()=  QuizQuestion(
    id = id,
    topicCode = topicCode,
    question = question,
    correctAnswer = correctAnswer,
  allOptions = (incorrectAnswers + correctAnswer).shuffled(),
    explanation = explanation

)


private fun QuizQuestionDto.toQuizQuestionsEntity()=  QuizQuestionEntity(
    id = id,
    topicCode = topicCode,
    question = question,
    correctAnswer = correctAnswer,
incorrectAnswers = incorrectAnswers,
    explanation = explanation

)


 fun QuizQuestionEntity.entityToQuizQuestions()=  QuizQuestion(
    id = id,
    topicCode = topicCode,
    question = question,
    correctAnswer = correctAnswer,
    allOptions = (incorrectAnswers + correctAnswer).shuffled(),
    explanation = explanation

)

fun List<QuizQuestionDto>.toQuizQuestions() = map { it.toQuizQuestions() }

fun List<QuizQuestionDto>.toQuizQuestionsEntity() = map { it.toQuizQuestionsEntity() }

fun List<QuizQuestionEntity>.entityToQuizQuestions() = map { it.entityToQuizQuestions() }