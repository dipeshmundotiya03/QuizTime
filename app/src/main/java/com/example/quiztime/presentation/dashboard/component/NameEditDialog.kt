package com.example.quiztime.presentation.dashboard.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun NameEditDialog(
    modifier: Modifier = Modifier,
    isOpen : Boolean,
    textFieldValue : String,
    usernameError : String?,
    title : String =" Edit your Name",
    confirmButtonText : String = "Rename",
    dismissButtonText : String = "Cancel",
    onTextFieldValueChange: (String) -> Unit,
    onDialogDismiss : () -> Unit,
    onConfirmButton :()-> Unit
){
    if (isOpen) {
        AlertDialog(
            modifier = modifier,
            title = { Text(text = title) },
            text = {
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = onTextFieldValueChange,
                    singleLine = true,
                    isError = usernameError != null && textFieldValue.isNotBlank(),
                    supportingText = { Text(text = usernameError.orEmpty()) }
                )
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
            },
        )
    }
}