package com.example.vitalflow.ui.screens.placeholder

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vitalflow.ui.components.AeroCard
import com.example.vitalflow.ui.theme.AeroTheme
import com.example.vitalflow.ui.theme.VitalFlowTheme

@Composable
fun PlaceholderScreen(
    navController: NavHostController,
    placeholderNumber: Int,
    isSettingsPlaceholder: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AeroCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = if (isSettingsPlaceholder) 
                        "Settings Placeholder $placeholderNumber" 
                    else 
                        "Module Placeholder $placeholderNumber",
                    style = AeroTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = "This module is coming soon!",
                    style = AeroTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = AeroTheme.colors.onSurfaceVariant
                )
                
                Text(
                    text = "Future functionality will be implemented here.",
                    style = AeroTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = AeroTheme.colors.onSurfaceVariant
                )
                
                Button(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Go Back")
                }
            }
        }
    }
}

@Composable
fun ModulePlaceholderContent(
    title: String,
    description: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AeroCard {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = title,
                    style = AeroTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = description,
                    style = AeroTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = AeroTheme.colors.onSurfaceVariant
                )
                
                Button(
                    onClick = onBackClick,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Go Back")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceholderScreenPreview() {
    VitalFlowTheme {
        Surface {
            ModulePlaceholderContent(
                title = "Module Placeholder 1",
                description = "This module is coming soon!",
                onBackClick = {}
            )
        }
    }
}
