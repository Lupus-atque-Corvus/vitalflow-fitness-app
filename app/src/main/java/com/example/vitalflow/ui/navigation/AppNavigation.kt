package com.example.vitalflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vitalflow.ui.screens.gym.GymScreen
import com.example.vitalflow.ui.screens.gym.ExerciseScreen
import com.example.vitalflow.ui.screens.outdoor.OutdoorScreen
import com.example.vitalflow.ui.screens.stretching.StretchingScreen
import com.example.vitalflow.ui.screens.supplements.SupplementsScreen
import com.example.vitalflow.ui.screens.settings.SettingsScreen
import com.example.vitalflow.ui.screens.placeholder.PlaceholderScreen
import com.example.vitalflow.ui.screens.training.TrainingScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Training.route
    ) {
        // Training Module
        composable(NavRoutes.Training.route) {
            TrainingScreen(navController)
        }
        
        // Gym Module
        composable(NavRoutes.Training.Gym.route) {
            GymScreen(navController)
        }
        composable(NavRoutes.Training.Gym.Plans.route) {
            PlaceholderScreen(navController, placeholderNumber = 1)
        }
        composable(NavRoutes.Training.Gym.Exercises.route) {
            ExerciseScreen(navController)
        }
        
        // Outdoor Module
        composable(NavRoutes.Training.Outdoor.route) {
            OutdoorScreen(navController)
        }
        composable(NavRoutes.Training.Outdoor.Running.route) {
            PlaceholderScreen(navController, placeholderNumber = 2)
        }
        
        // Stretching Module
        composable(NavRoutes.Training.Stretching.route) {
            StretchingScreen(navController)
        }
        composable(NavRoutes.Training.Stretching.Exercises.route) {
            PlaceholderScreen(navController, placeholderNumber = 3)
        }
        
        // Supplements Module
        composable(NavRoutes.Training.Supplements.route) {
            SupplementsScreen(navController)
        }
        composable(NavRoutes.Training.Supplements.Tracking.route) {
            PlaceholderScreen(navController, placeholderNumber = 4)
        }
        
        // Settings Module
        composable(NavRoutes.Settings.route) {
            SettingsScreen(navController)
        }
        composable(NavRoutes.Settings.Language.route) {
            LanguageSettingsScreen(navController)
        }
        
        // Placeholder Screens
        composable(NavRoutes.Placeholder1.route) {
            PlaceholderScreen(
                navController = navController,
                placeholderNumber = 1
            )
        }
        composable(NavRoutes.Placeholder2.route) {
            PlaceholderScreen(
                navController = navController,
                placeholderNumber = 2
            )
        }
        composable(NavRoutes.Placeholder3.route) {
            PlaceholderScreen(
                navController = navController,
                placeholderNumber = 3
            )
        }
        composable(NavRoutes.Placeholder4.route) {
            PlaceholderScreen(
                navController = navController,
                placeholderNumber = 4
            )
        }
        composable(NavRoutes.Placeholder5.route) {
            PlaceholderScreen(
                navController = navController,
                placeholderNumber = 5
            )
        }
        composable(NavRoutes.Placeholder6.route) {
            PlaceholderScreen(
                navController = navController,
                placeholderNumber = 6
            )
        }
        
        // Settings Placeholders
        composable(NavRoutes.Settings.Placeholder1.route) {
            PlaceholderScreen(
                navController = navController,
                placeholderNumber = 1,
                isSettingsPlaceholder = true
            )
        }
        composable(NavRoutes.Settings.Placeholder2.route) {
            PlaceholderScreen(
                navController = navController,
                placeholderNumber = 2,
                isSettingsPlaceholder = true
            )
        }
    }
}

