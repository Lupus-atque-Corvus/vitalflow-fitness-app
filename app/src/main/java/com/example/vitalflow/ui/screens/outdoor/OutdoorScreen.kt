package com.example.vitalflow.ui.screens.outdoor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vitalflow.R
import com.example.vitalflow.ui.components.AeroCard
import com.example.vitalflow.ui.theme.AeroTheme
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun OutdoorScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var isTracking by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf(0.seconds) }
    var currentDistance by remember { mutableStateOf(0f) }
    var currentPace by remember { mutableStateOf(0f) }
    
    var runs by remember { mutableStateOf(listOf<Run>()) }

    // Simulated timer for demo
    LaunchedEffect(isTracking, isPaused) {
        while (isTracking && !isPaused) {
            kotlinx.coroutines.delay(1000)
            currentTime += 1.seconds
            // Simulate distance increase (random pace between 4-6 min/km)
            currentDistance += (1f / (240f + (Math.random() * 120f).toFloat()))
            currentPace = if (currentDistance > 0) {
                (currentTime.inWholeSeconds / 60f) / (currentDistance)
            } else 0f
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Current Run Card
        AeroCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.outdoor_running),
                    style = AeroTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatDisplay(
                        label = stringResource(R.string.outdoor_distance),
                        value = String.format("%.2f km", currentDistance)
                    )
                    StatDisplay(
                        label = stringResource(R.string.outdoor_time),
                        value = formatDuration(currentTime)
                    )
                    StatDisplay(
                        label = stringResource(R.string.outdoor_pace),
                        value = String.format("%.1f min/km", currentPace)
                    )
                }

                // Control Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (!isTracking) {
                        Button(
                            onClick = { 
                                isTracking = true
                                isPaused = false
                            }
                        ) {
                            Text(stringResource(R.string.outdoor_start))
                        }
                    } else {
                        Button(
                            onClick = {
                                isPaused = !isPaused
                            }
                        ) {
                            Text(
                                if (isPaused) stringResource(R.string.outdoor_resume)
                                else stringResource(R.string.outdoor_pause)
                            )
                        }
                        Button(
                            onClick = {
                                isTracking = false
                                // Save run
                                if (currentDistance > 0) {
                                    runs = runs + Run(
                                        date = Date(),
                                        distance = currentDistance,
                                        duration = currentTime,
                                        averagePace = currentPace
                                    )
                                }
                                // Reset stats
                                currentTime = 0.seconds
                                currentDistance = 0f
                                currentPace = 0f
                            }
                        ) {
                            Text(stringResource(R.string.outdoor_stop))
                        }
                    }
                }
            }
        }

        // History Card
        AeroCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "History",
                    style = AeroTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(runs.sortedByDescending { it.date }) { run ->
                        RunHistoryItem(run = run)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatDisplay(
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
            style = AeroTheme.typography.bodyMedium,
            color = AeroTheme.colors.onSurfaceVariant
        )
    }
}

@Composable
private fun RunHistoryItem(
    run: Run,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AeroTheme.colors.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                        .format(run.date),
                    style = AeroTheme.typography.bodyMedium
                )
                Text(
                    text = String.format("%.2f km", run.distance),
                    style = AeroTheme.typography.titleMedium
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = formatDuration(run.duration),
                    style = AeroTheme.typography.bodyMedium
                )
                Text(
                    text = String.format("%.1f min/km", run.averagePace),
                    style = AeroTheme.typography.bodyMedium
                )
            }
        }
    }
}

private fun formatDuration(duration: Duration): String {
    val hours = duration.inWholeHours
    val minutes = (duration.inWholeMinutes % 60)
    val seconds = (duration.inWholeSeconds % 60)
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

data class Run(
    val date: Date,
    val distance: Float, // in kilometers
    val duration: Duration,
    val averagePace: Float // minutes per kilometer
)

@Preview(showBackground = true)
@Composable
fun OutdoorScreenPreview() {
    MaterialTheme {
        OutdoorScreen(
            navController = rememberNavController()
        )
    }
}
