package com.example.quiztime.presentation.quiz.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuizSubmitButtons(
    modifier: Modifier = Modifier,
    isPreviousButtonEnabled : Boolean,
    isNextButtonEnabled : Boolean,
    onPreviousButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    onSubmitButtonClick: () -> Unit
){
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ){
        OutlinedIconButton(
            onClick = onPreviousButtonClick,
            enabled = isPreviousButtonEnabled
        ) {
           Icon(
               imageVector = Icons.AutoMirrored.Filled.ArrowBack,
               contentDescription = "Previous"
           )
        }
        Button(
            modifier = Modifier.padding(horizontal = 30.dp),
            onClick = onSubmitButtonClick
        ) {
            Text(modifier = Modifier.padding(horizontal = 10.dp),
                text = "Submit")
        }
        OutlinedIconButton(
            onClick = onNextButtonClick,
            enabled = isNextButtonEnabled


        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next"
            )
        }
    }

}