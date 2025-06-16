package com.example.vitalflow.ui.screens.training

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitalflow.R
import com.example.vitalflow.ui.components.AeroCard
import com.example.vitalflow.ui.navigation.NavRoutes
import com.example.vitalflow.ui.theme.AeroTheme

@Composable
fun TrainingScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Main Training Modules
        ModuleSection(
            title = stringResource(R.string.nav_training),
            modules = listOf(
                ModuleItem(
                    titleRes = R.string.training_gym,
                    route = NavRoutes.Training.Gym.route
                ),
                ModuleItem(
                    titleRes = R.string.training_outdoor,
                    route = NavRoutes.Training.Outdoor.route
                ),
                ModuleItem(
                    titleRes = R.string.training_stretching,
                    route = NavRoutes.Training.Stretching.route
                ),
                ModuleItem(
                    titleRes = R.string.training_supplements,
                    route = NavRoutes.Training.Supplements.route
                )
            ),
            onModuleClick = { route ->
                navController.navigate(route)
            }
        )

        // Placeholder Modules
        PlaceholderSection(
            navController = navController
        )
    }
}

@Composable
private fun ModuleSection(
    title: String,
    modules: List<ModuleItem>,
    onModuleClick: (String) -> Unit
) {
    AeroCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = AeroTheme.typography.headlineMedium,
                color = AeroTheme.colors.onSurface
            )
            
            modules.forEach { module ->
                ModuleButton(
                    titleRes = module.titleRes,
                    onClick = { onModuleClick(module.route) }
                )
            }
        }
    }
}

@Composable
private fun PlaceholderSection(
    navController: NavHostController
) {
    AeroCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.coming_soon),
                style = AeroTheme.typography.headlineMedium,
                color = AeroTheme.colors.onSurface
            )
            
            // 6 placeholder modules as specified
            repeat(6) { index ->
                ModuleButton(
                    titleRes = R.string.placeholder_module,
                    titleFormatArg = index + 1,
                    onClick = {
                        when (index) {
                            0 -> navController.navigate(NavRoutes.Placeholder1.route)
                            1 -> navController.navigate(NavRoutes.Placeholder2.route)
                            2 -> navController.navigate(NavRoutes.Placeholder3.route)
                            3 -> navController.navigate(NavRoutes.Placeholder4.route)
                            4 -> navController.navigate(NavRoutes.Placeholder5.route)
                            5 -> navController.navigate(NavRoutes.Placeholder6.route)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun ModuleButton(
    titleRes: Int,
    titleFormatArg: Int? = null,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = AeroTheme.colors.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Text(
            text = if (titleFormatArg != null) {
                stringResource(titleRes, titleFormatArg)
            } else {
                stringResource(titleRes)
            },
            style = AeroTheme.typography.titleMedium,
            color = AeroTheme.colors.onSurfaceVariant
        )
    }
}

private data class ModuleItem(
    val titleRes: Int,
    val route: String
)

@Preview(showBackground = true)
@Composable
fun TrainingScreenPreview() {
    MaterialTheme {
        TrainingScreen(
            navController = rememberNavController()
        )
    }
}
