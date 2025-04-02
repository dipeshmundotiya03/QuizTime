package com.example.quiztime.presentation.quiz.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun ExitQuizDialog(
    modifier: Modifier = Modifier,
    isOpen : Boolean,

    title : String =" Exit Quiz?",
    confirmButtonText : String = "Exit",
    dismissButtonText : String = "No",

    onDialogDismiss : () -> Unit,
    onConfirmButton :()-> Unit
){
    if (isOpen) {
        AlertDialog(
            modifier = modifier,
            title = { Text(text = title) },
            text = {
                Text(text = "Are you sure ,you want to Exit the quiz? you won't be able to continue from where you left.")
            },
            onDismissRequest = onDialogDismiss,
            confirmButton = {
                TextButton(
                    onClick = onConfirmButton
                ) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDialogDismiss
                ) {
                    Text(text = dismissButtonText)
                }
            }
        )
    }
}