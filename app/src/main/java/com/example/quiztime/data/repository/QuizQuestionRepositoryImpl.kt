package com.example.quiztime.data.repository

import android.annotation.SuppressLint
import com.example.domain.util.DataError
import com.example.quiztime.data.local.dao.QuizQuestionDao
import com.example.quiztime.data.local.dao.UserAnswerDao
import com.example.quiztime.data.mapper.entityToQuizQuestions
import com.example.quiztime.data.mapper.toQuizQuestions
import com.example.quiztime.data.mapper.toQuizQuestionsEntity
import com.example.quiztime.data.mapper.toUserAnswers
import com.example.quiztime.data.mapper.toUserAnswersEntity
import com.example.quiztime.data.remote.RemoteQuizDataSource
import com.example.quiztime.domain.model.QuizQuestion
import com.example.quiztime.domain.model.UserAnswer
import com.example.quiztime.domain.repository.QuizQuestionRepository
import domain.util.Result

class QuizQuestionRepositoryImpl(
    private val remoteDataSource: RemoteQuizDataSource,
    private val questionDao: QuizQuestionDao,
    private val answerDao: UserAnswerDao
) : QuizQuestionRepository {

    @SuppressLint("SuspiciousIndentation")
    override suspend fun fetchAndSaveQuizQuestions(topicCode: Int): Result<List<QuizQuestion>, DataError> {
        return when (val result = remoteDataSource.getQuizQuestions(topicCode)) {
            is Result.Success -> {
                val questionDto = result.data
                questionDao.clearAllQuizQuestions()
                questionDao.insertQuizQuestions(questionDto.toQuizQuestionsEntity())
                Result.Success(questionDto.toQuizQuestions())
            }

            is Result.Failure -> result
        }
    }

    override suspend fun getQuizQuestions(): Result<List<QuizQuestion>, DataError> {
       return try {
            val questionsEntity  = questionDao.getAllQuizQuestions()
           if(questionsEntity.isNotEmpty()){
               Result.Success(questionsEntity.entityToQuizQuestions())
           }else{
               Result.Failure(DataError.Unknown("No Questions Found"))
           }

       }catch (e: Exception){
           e.printStackTrace()
           Result.Failure(DataError.Unknown(e.message))
       }
    }

    override suspend fun getQuizQuestionById(questionId: String): Result<QuizQuestion, DataError> {
        return try {
         val questionEntity = questionDao.getQuizQuestionById(questionId)
         if(questionEntity != null){
             Result.Success(questionEntity.entityToQuizQuestions())
         }else{
             Result.Failure(DataError.Unknown("No Question Found"))
         }
        }catch (e: Exception){
            e.printStackTrace()
            Result.Failure(DataError.Unknown(e.message))
        }

    }

    override suspend fun saveUserAnswers(userAnswers: List<UserAnswer>): Result<Unit, DataError> {
       return try {
              val answerEntity = userAnswers.toUserAnswersEntity()
           answerDao.insertUserAnswer(answerEntity)
           Result.Success(Unit)
       }catch(e: Exception){
          Result.Failure(DataError.Unknown(e.message))
        }
    }

    override suspend fun getUserAnswers(): Result<List<UserAnswer>, DataError> {
        return try {
            val answersEntity = answerDao.getAllUserAnswers()
            if(answersEntity.isNotEmpty()){
                Result.Success(answersEntity.toUserAnswers())
            }else{
                Result.Failure(DataError.Unknown("No Answers Found"))
            }
        }catch (e:Exception){
            Result.Failure(DataError.Unknown(e.message))
        }
    }

}