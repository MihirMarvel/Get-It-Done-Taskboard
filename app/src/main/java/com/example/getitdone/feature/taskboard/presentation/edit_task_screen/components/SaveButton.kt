package com.example.getitdone.feature.taskboard.presentation.edit_task_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getitdone.R
import com.example.getitdone.feature.taskboard.presentation.edit_task_screen.EditTaskViewModel
import kotlinx.coroutines.launch

@Composable
fun SaveButton(
    viewModel: EditTaskViewModel,
    onSaved: () -> Unit,
){
    val scope = rememberCoroutineScope()

    Button(
        enabled = !viewModel.isLoading,
        onClick = {
            scope.launch {
                val ok = viewModel.save()
                if (ok) onSaved()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 6.dp
        )
    ) {
        Text(
            text = stringResource(R.string.save),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.White,
            ),
            fontWeight = FontWeight.SemiBold,
        )
    }
}