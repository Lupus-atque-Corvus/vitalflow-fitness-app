package com.example.vitalflow.ui.screens.gym

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vitalflow.R
import com.example.vitalflow.ui.components.AeroCard
import com.example.vitalflow.ui.navigation.NavRoutes
import com.example.vitalflow.ui.theme.AeroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var selectedSplit by remember { mutableStateOf<TrainingSplit?>(null) }
    var showSplitDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Training Plans Section
        AeroCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.gym_plans),
                    style = AeroTheme.typography.headlineMedium
                )

                OutlinedButton(
                    onClick = { showSplitDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = selectedSplit?.let { stringResource(it.nameRes) }
                            ?: stringResource(R.string.plan_full_body)
                    )
                }

                if (selectedSplit != null) {
                    Text(
                        text = stringResource(selectedSplit!!.frequencyRes),
                        style = AeroTheme.typography.bodyMedium,
                        color = AeroTheme.colors.onSurfaceVariant
                    )
                }
            }
        }

        // Exercises Section
        AeroCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.gym_exercises),
                    style = AeroTheme.typography.headlineMedium
                )

                OutlinedButton(
                    onClick = { navController.navigate(NavRoutes.Training.Gym.Exercises.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.gym_add_exercise))
                }
            }
        }

        // Quick Stats Card
        AeroCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Quick Stats",
                    style = AeroTheme.typography.headlineMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        label = "Exercises",
                        value = "0"
                    )
                    StatItem(
                        label = "Workouts",
                        value = "0"
                    )
                    StatItem(
                        label = "Progress",
                        value = "0%"
                    )
                }
            }
        }
    }

    if (showSplitDialog) {
        AlertDialog(
            onDismissRequest = { showSplitDialog = false },
            title = {
                Text(stringResource(R.string.gym_plans))
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TrainingSplit.values().forEach { split ->
                        OutlinedButton(
                            onClick = {
                                selectedSplit = split
                                showSplitDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(split.nameRes))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showSplitDialog = false }
                ) {
                    Text(stringResource(R.string.action_cancel))
                }
            }
        )
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = AeroTheme.typography.titleLarge,
            color = AeroTheme.colors.primary
        )
        Text(
            text = label,
            style = AeroTheme.typography.bodySmall,
            color = AeroTheme.colors.onSurfaceVariant
        )
    }
}

private enum class TrainingSplit(
    val nameRes: Int,
    val frequencyRes: Int
) {
    FULL_BODY(
        R.string.plan_full_body,
        R.string.plan_3x_week
    ),
    UPPER_LOWER(
        R.string.plan_upper_lower,
        R.string.plan_4x_week
    ),
    PUSH_PULL_LEGS(
        R.string.plan_push_pull_legs,
        R.string.plan_6x_week
    )
}

@Preview(showBackground = true)
@Composable
fun GymScreenPreview() {
    MaterialTheme {
        GymScreen(
            navController = rememberNavController()
        )
    }
}
