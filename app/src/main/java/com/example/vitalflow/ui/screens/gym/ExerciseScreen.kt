package com.example.vitalflow.ui.screens.gym

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vitalflow.R
import com.example.vitalflow.ui.components.AeroCard
import com.example.vitalflow.ui.theme.AeroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var exercises by remember { mutableStateOf(listOf<Exercise>()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Add Exercise Button
        OutlinedButton(
            onClick = { showAddDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.gym_add_exercise))
        }

        // Exercise List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(exercises) { exercise ->
                ExerciseCard(
                    exercise = exercise,
                    onDelete = {
                        exercises = exercises.filter { it != exercise }
                    },
                    onEdit = { updatedExercise ->
                        exercises = exercises.map { 
                            if (it == exercise) updatedExercise else it 
                        }
                    }
                )
            }
        }
    }

    if (showAddDialog) {
        ExerciseDialog(
            onDismiss = { showAddDialog = false },
            onSave = { exercise ->
                exercises = exercises + exercise
                showAddDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExerciseDialog(
    exercise: Exercise? = null,
    onDismiss: () -> Unit,
    onSave: (Exercise) -> Unit
) {
    var name by remember { mutableStateOf(exercise?.name ?: "") }
    var category by remember { mutableStateOf(exercise?.category ?: "") }
    var sets by remember { mutableStateOf(exercise?.sets?.size?.toString() ?: "3") }
    var weight by remember { mutableStateOf(exercise?.sets?.firstOrNull()?.weight?.toString() ?: "0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(
                    if (exercise == null) R.string.gym_add_exercise
                    else R.string.action_edit
                )
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.gym_exercise_name)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text(stringResource(R.string.gym_category)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = sets,
                        onValueChange = { sets = it },
                        label = { Text(stringResource(R.string.gym_sets)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text(stringResource(R.string.gym_weight)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val newExercise = Exercise(
                        name = name,
                        category = category,
                        sets = List(sets.toIntOrNull() ?: 3) {
                            ExerciseSet(weight.toFloatOrNull() ?: 0f)
                        }
                    )
                    onSave(newExercise)
                }
            ) {
                Text(stringResource(R.string.action_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.action_cancel))
            }
        }
    )
}

@Composable
private fun ExerciseCard(
    exercise: Exercise,
    onDelete: () -> Unit,
    onEdit: (Exercise) -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }

    AeroCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = exercise.name,
                        style = AeroTheme.typography.titleMedium
                    )
                    Text(
                        text = exercise.category,
                        style = AeroTheme.typography.bodyMedium,
                        color = AeroTheme.colors.onSurfaceVariant
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = { showEditDialog = true }) {
                        Text(stringResource(R.string.action_edit))
                    }
                    IconButton(onClick = onDelete) {
                        Text(stringResource(R.string.action_delete))
                    }
                }
            }

            Text(
                text = "${exercise.sets.size} sets × ${exercise.sets.first().weight}kg",
                style = AeroTheme.typography.bodyMedium
            )
        }
    }

    if (showEditDialog) {
        ExerciseDialog(
            exercise = exercise,
            onDismiss = { showEditDialog = false },
            onSave = { updatedExercise ->
                onEdit(updatedExercise)
                showEditDialog = false
            }
        )
    }
}

data class Exercise(
    val name: String,
    val category: String,
    val sets: List<ExerciseSet>
)

data class ExerciseSet(
    val weight: Float,
    val completed: Boolean = false
)

@Preview(showBackground = true)
@Composable
fun ExerciseScreenPreview() {
    MaterialTheme {
        ExerciseScreen(
            navController = rememberNavController()
        )
    }
}
