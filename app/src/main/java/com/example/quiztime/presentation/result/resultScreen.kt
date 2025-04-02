package com.example.quiztime.presentation.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.quiztime.R
import com.example.quiztime.domain.model.QuizQuestion
import com.example.quiztime.presentation.theme.CustomGreen

@Composable
fun ResultScreen(
    state: ResultState,
    onReportClick : (String) -> Unit ,
    onStartNewQuizClick : () -> Unit
){

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyColumn (
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(10.dp)
        ) {
            item {
                Scorecard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 80.dp, horizontal = 10.dp),
                    scorePercentage = state.scorePercentage,
                    correctAnswerCount = state.correctAnswerCount,
                    totalQuestionCount = state.totalQuestionCount
                )
            }
            item {
                Text(
                    text = "Quiz Questions",
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = TextDecoration.Underline
                )
            }
            items(state.quizQuestions) { question ->
                val userAnswer = state.userAnswers
                    .find { it.questionId == question.id }?.selectedOption
                QuestionItem(question = question,
                userSelectedAnswer = userAnswer,
                    onReportClick = {onReportClick(question.id)})
            }
        }
        Button(
            modifier = Modifier.padding(10.dp)
                .align(Alignment.CenterHorizontally),
            onClick = onStartNewQuizClick

        ) {
            Text(text = "Start New Quiz")
        }

    }
}

@Composable
private fun Scorecard(
    modifier: Modifier = Modifier,
    scorePercentage : Int,
    correctAnswerCount : Int ,
    totalQuestionCount : Int
){
    val resultText = when(scorePercentage){
        in 71..100 -> "Congratulations!\n A great Performance"
        in 41..70 -> "You did well,\n but there's room for improvement"
        else -> "You may have struggled this time "
    }
    val resultIconResId = when(scorePercentage){
        in 71..100 ->R.drawable.smile
        in 41..70 -> R.drawable.smile
        else -> R.drawable.sad
    }
   Card (
       modifier =  modifier
   ){

           Image(
               modifier = Modifier
                   .align(Alignment.CenterHorizontally)
                   .padding(20.dp)
                   .size(100.dp),
               painter = painterResource(resultIconResId),
               contentDescription = null
           )
           Text(
               modifier =  Modifier.align(Alignment.CenterHorizontally),
               text = "You Answered correctly $correctAnswerCount out of $totalQuestionCount.",
               style = MaterialTheme.typography.titleMedium,

           )
           Text(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(10.dp),
               text = resultText,
               style = MaterialTheme.typography.bodyMedium,
               textAlign = TextAlign.Center
           )

   }
}

@Composable
private fun QuestionItem(
    modifier: Modifier = Modifier,
    question :QuizQuestion,
    userSelectedAnswer : String?,
    onReportClick : () -> Unit = {}
){

    Column(
        modifier = modifier
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(
                modifier = Modifier.weight(1f),
                text =" Q: ${question.question}",
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onReportClick) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Report"
                )
            }
        }

        question.allOptions.forEachIndexed() {index, option->
            val letter = when(index){
                0 -> "(a)"
                1 -> "(b)"
                2 -> "(c)"
                3 -> "(d)"
                else -> ""
            }

            val optionColor = when{
                option == question.correctAnswer -> CustomGreen
                option == userSelectedAnswer -> MaterialTheme.colorScheme.error
                else -> LocalContentColor.current
            }
            Text(
                text = letter + option,
                color = optionColor
            )
        }
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = question.explanation,
            color = MaterialTheme.colorScheme.onBackground
        )
        HorizontalDivider()
    }
}