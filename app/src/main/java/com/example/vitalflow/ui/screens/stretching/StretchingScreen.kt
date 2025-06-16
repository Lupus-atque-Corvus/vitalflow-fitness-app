package com.example.vitalflow.ui.screens.stretching

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.vitalflow.R
import com.example.vitalflow.ui.components.AeroCard
import com.example.vitalflow.ui.theme.AeroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StretchingScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var exercises by remember { mutableStateOf(defaultStretchingExercises) }
    var selectedExercise by remember { mutableStateOf<StretchingExercise?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header with Add Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.stretching_exercises),
                style = AeroTheme.typography.headlineMedium
            )
            
            OutlinedButton(
                onClick = { showAddDialog = true }
            ) {
                Text(stringResource(R.string.stretching_add))
            }
        }

        // Exercise Grid
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(exercises) { exercise ->
                StretchingExerciseCard(
                    exercise = exercise,
                    onClick = { selectedExercise = exercise }
                )
            }
        }
    }

    // Add Exercise Dialog
    if (showAddDialog) {
        AddExerciseDialog(
            onDismiss = { showAddDialog = false },
            onSave = { exercise ->
                exercises = exercises + exercise
                showAddDialog = false
            }
        )
    }

    // Exercise Detail Dialog
    if (selectedExercise != null) {
        ExerciseDetailDialog(
            exercise = selectedExercise!!,
            onDismiss = { selectedExercise = null },
            onDelete = { exercise ->
                exercises = exercises.filter { it != exercise }
                selectedExercise = null
            }
        )
    }
}

@Composable
private fun StretchingExerciseCard(
    exercise: StretchingExercise,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AeroCard(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Exercise Image
            AsyncImage(
                model = exercise.imageUrl,
                contentDescription = exercise.name,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Exercise Name
            Text(
                text = exercise.name,
                style = AeroTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                maxLines = 2
            )

            // Duration
            Text(
                text = "${exercise.duration} sec",
                style = AeroTheme.typography.bodySmall,
                color = AeroTheme.colors.onSurfaceVariant
            )

            OutlinedButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Details")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddExerciseDialog(
    onDismiss: () -> Unit,
    onSave: (StretchingExercise) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("30") }
    var imageUrl by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.stretching_add))
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Exercise Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text(stringResource(R.string.stretching_duration)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        StretchingExercise(
                            name = name,
                            duration = duration.toIntOrNull() ?: 30,
                            imageUrl = imageUrl,
                            description = description
                        )
                    )
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
private fun ExerciseDetailDialog(
    exercise: StretchingExercise,
    onDismiss: () -> Unit,
    onDelete: (StretchingExercise) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(exercise.name)
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = exercise.imageUrl,
                    contentDescription = exercise.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Duration: ${exercise.duration} seconds",
                    style = AeroTheme.typography.bodyLarge
                )

                Text(
                    text = exercise.description,
                    style = AeroTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.action_back))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDelete(exercise) }
            ) {
                Text(
                    text = stringResource(R.string.action_delete),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

data class StretchingExercise(
    val name: String,
    val duration: Int, // in seconds
    val imageUrl: String,
    val description: String
)

// Sample exercises
private val defaultStretchingExercises = listOf(
    StretchingExercise(
        name = "Hamstring Stretch",
        duration = 30,
        imageUrl = "https://images.pexels.com/photos/4056535/pexels-photo-4056535.jpeg",
        description = "Sit on the floor with one leg extended. Reach for your toes while keeping your back straight."
    ),
    StretchingExercise(
        name = "Shoulder Stretch",
        duration = 20,
        imageUrl = "https://images.pexels.com/photos/4662438/pexels-photo-4662438.jpeg",
        description = "Pull one arm across your chest, holding it with your other arm."
    ),
    StretchingExercise(
        name = "Quad Stretch",
        duration = 25,
        imageUrl = "https://images.pexels.com/photos/4662344/pexels-photo-4662344.jpeg",
        description = "Stand on one leg, pull your other foot behind you towards your buttocks."
    )
)

@Preview(showBackground = true)
@Composable
fun StretchingScreenPreview() {
    MaterialTheme {
        StretchingScreen(
            navController = rememberNavController()
        )
    }
}
