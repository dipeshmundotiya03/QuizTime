package com.example.quiztime.presentation.dashboard.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.quiztime.R
import com.example.quiztime.presentation.theme.CustomBlue
import com.example.quiztime.presentation.theme.CustomPink

@Composable
fun UserStaticsCard(
    modifier: Modifier = Modifier,
    questionsAttempted : Int,
    correctAnswers : Int
) {
    val barProgress = if(questionsAttempted >0){
        correctAnswers.toFloat() / questionsAttempted
    }else 0f

    Card(modifier= Modifier) {
        ProgressBar(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(15.dp),
            barProgress = barProgress
        )
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            UserStatics(
                value = questionsAttempted,
                description = "Questions Attempted",
                iconResId = R.drawable.ic_add_circle
            )
            UserStatics(
                value = correctAnswers,
                description = "Correct Answers",
                iconResId = R.drawable.ic_check_circle
            )
        }

    }


}

@Composable
private fun ProgressBar(
    modifier: Modifier = Modifier,
    barProgress : Float,
    gradientColors : List<Color> = listOf(CustomPink, CustomBlue)
){
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .background(Color.White)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth(barProgress)
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.extraSmall)
                .background(Brush.linearGradient(gradientColors))
        )

        Box(
            modifier = Modifier
                .padding(end = 5.dp)
                .align(Alignment.CenterEnd)
                .size(5.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(gradientColors))
        )
    }

}


@Composable
private fun UserStatics(
    modifier: Modifier = Modifier,
    value : Int,
    description : String,
    iconResId : Int
){
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = modifier
                .size(50.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ){

           Icon(painter = painterResource(iconResId),
               contentDescription = description,
               tint = MaterialTheme.colorScheme.secondary)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = "$value",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }


}


@PreviewLightDark
@Composable
private fun PreviewUserStaticsCard(){
    UserStaticsCard(
        questionsAttempted = 10,
        correctAnswers = 8
    )

}