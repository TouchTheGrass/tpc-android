package ru.touchthegrass.tpc.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun ConsiderDialog(
    openDialog: MutableState<Boolean>,
    onConsiderPressed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(text = "Consider")
        },
        text = {
            Text(text = "Are you sure you want to consider the game?")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                    onConsiderPressed()
                }
            ) {
                Text("Consider")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text("Cancel")
            }
        }
    )
}