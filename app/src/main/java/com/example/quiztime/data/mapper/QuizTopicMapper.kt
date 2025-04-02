package com.example.quiztime.data.mapper

import com.example.quiztime.data.local.Entity.QuizTopicEntity
import com.example.quiztime.data.remote.dto.QuizTopicDto
import com.example.quiztime.data.util.Constant.BASE_URL
import com.example.quiztime.domain.model.QuizTopic


private fun QuizTopicDto.toQuizTopics() = QuizTopic(
    id = id,
    name =  name,
    imageUrl = BASE_URL +imageUrl,
    code =  code
)

private fun QuizTopicDto.toQuizTopicsEntity() = QuizTopicEntity(
    id = id,
    name =  name,
    imageUrl = BASE_URL +imageUrl,
    code =  code
)

private fun QuizTopicEntity.entityToQuizTopic() = QuizTopic(
    id = id,
    name =  name,
    imageUrl = imageUrl,
    code =  code
)

fun List<QuizTopicDto>.toQuizTopics() = map { it.toQuizTopics() }

fun List<QuizTopicDto>.toQuizTopicsEntity() = map { it.toQuizTopicsEntity() }

fun List<QuizTopicEntity>.entityToQuizTopics() = map { it.entityToQuizTopic() }