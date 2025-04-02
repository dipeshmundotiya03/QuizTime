package com.example.quiztime.data.mapper

import com.example.quiztime.data.remote.dto.QuizQuestionDto

import com.example.quiztime.domain.model.QuizQuestion

fun QuizQuestionDto.toQuizQuestions()=  QuizQuestion(
    id = id,
    topicCode = topicCode,
    question = question,
    correctAnswer = correctAnswer,
  allOptions = (incorrectAnswers + correctAnswer).shuffled(),
    explanation = explanation

)

fun List<QuizQuestionDto>.toQuizQuestions() = map { it.toQuizQuestions() }