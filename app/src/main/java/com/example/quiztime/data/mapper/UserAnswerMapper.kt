package com.example.quiztime.data.mapper

import com.example.quiztime.data.local.Entity.UserAnswerEntity
import com.example.quiztime.domain.model.UserAnswer

fun UserAnswer.toUserAnswerEntity() = UserAnswerEntity(
    questionId = questionId,
    selectedOption = selectedOption
)

fun UserAnswerEntity.toUserAnswer() = UserAnswer(
    questionId = questionId,
    selectedOption = selectedOption
)

fun List<UserAnswerEntity>.toUserAnswers() = map { it.toUserAnswer() }

fun List<UserAnswer>.toUserAnswersEntity() = map { it.toUserAnswerEntity() }