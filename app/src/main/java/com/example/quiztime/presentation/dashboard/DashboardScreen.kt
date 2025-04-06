package com.example.quiztime.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiztime.domain.model.QuizTopic
import com.example.quiztime.presentation.common_component.ErrorScreen
import com.example.quiztime.presentation.dashboard.component.NameEditDialog
import com.example.quiztime.presentation.dashboard.component.ShimmerEffect
import com.example.quiztime.presentation.dashboard.component.TopicCard
import com.example.quiztime.presentation.dashboard.component.UserStaticsCard

@Composable
fun DashboardScreen(
    state: DashboardState,
    onAction:( DashboardAction)-> Unit,
    onTopicCardClick: (Int) -> Unit
) {
    NameEditDialog(
        isOpen = state.isNameEditDialogOpen,
        textFieldValue = state.usernameTextFieldValue,
        usernameError = state.usernameError,
        onDialogDismiss = {onAction(DashboardAction.NameEditDialogDismiss)},
        onConfirmButton = {onAction(DashboardAction.NameEditDialogConfirm)},
        onTextFieldValueChange = {onAction(DashboardAction.SetUserName(it))}
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        HeaderSection(
            modifier = Modifier
                .fillMaxWidth(),
            username = state.username,
            questionsAttempted = state.questionAttempted,
            correctAnswers = state.correctAnswers,
            onEditNameClick = {onAction(DashboardAction.NameEditIconClick)}
        )
        QuizTopicSection(
            modifier = Modifier.fillMaxWidth(),
            quizTopics = state.quizTopics,
            isTopicLoading = state.isLoading,
            errorMessage = state.errorMessage,
            onRefreshClick = {onAction(DashboardAction.RefreshIconClick)},
            onTopicCardClick = onTopicCardClick
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier,
    username : String,
    questionsAttempted : Int,
    correctAnswers : Int,
    onEditNameClick: () -> Unit

){
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Column(
            modifier = Modifier
                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Hello",
                style = MaterialTheme.typography.bodyMedium
            )
            Row {
                Text(
                    text = username,
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    modifier = Modifier.offset(x = (-10).dp,y = (-20).dp),
                    onClick = onEditNameClick
                ) {
                    Icon(
                        modifier = Modifier.size(15.dp),
                        imageVector = Icons.Default.Create,
                        contentDescription = "Edit Name"
                    )
                }
            }


        }
        UserStaticsCard(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .padding(20.dp),
            questionsAttempted =questionsAttempted,
            correctAnswers = correctAnswers
        )
    }



}
@Composable
private fun QuizTopicSection(
    modifier: Modifier = Modifier,
    quizTopics : List<QuizTopic>,
    isTopicLoading : Boolean,
    errorMessage : String?,
    onRefreshClick : () -> Unit,
    onTopicCardClick : (Int) -> Unit
){
    Column (modifier = modifier){
        Text(
            modifier = Modifier.padding(10.dp),
            text = "What topic do you want to learn today?",
            style = MaterialTheme.typography.titleLarge
        )
        if(errorMessage != null){
           ErrorScreen(
           modifier = Modifier
               .fillMaxWidth()
               .padding(20.dp),
           errorMessage = errorMessage,
           onRefreshClick = onRefreshClick
       )
        }else{
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = PaddingValues(15.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                if(isTopicLoading){
                    items(count = 6){
                        ShimmerEffect(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(MaterialTheme.shapes.small)
                                .background(MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }
                }else{
                    items(quizTopics){topic->
                        TopicCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                            , topicName = topic.name,
                            imageUrl = topic.imageUrl,
                            onClick = {onTopicCardClick(topic.code)}
                        )
                    }
                }


            }
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewDashboardScreen(){
    DashboardScreen(state = DashboardState(),
        onAction = {},
        onTopicCardClick = {})
}