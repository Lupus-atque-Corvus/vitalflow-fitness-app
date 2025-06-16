package com.example.vitalflow.ui.navigation

sealed class NavRoutes(val route: String) {
    // Main modules
    object Training : NavRoutes("training") {
        object Gym : NavRoutes("training/gym") {
            object Plans : NavRoutes("training/gym/plans")
            object Exercises : NavRoutes("training/gym/exercises")
        }
        object Outdoor : NavRoutes("training/outdoor") {
            object Running : NavRoutes("training/outdoor/running")
        }
        object Stretching : NavRoutes("training/stretching") {
            object Exercises : NavRoutes("training/stretching/exercises")
        }
        object Supplements : NavRoutes("training/supplements") {
            object Tracking : NavRoutes("training/supplements/tracking")
        }
    }

    // Settings
    object Settings : NavRoutes("settings") {
        object Language : NavRoutes("settings/language")
        object Placeholder1 : NavRoutes("settings/placeholder1")
        object Placeholder2 : NavRoutes("settings/placeholder2")
    }

    // Placeholders for future modules
    object Placeholder1 : NavRoutes("placeholder1")
    object Placeholder2 : NavRoutes("placeholder2")
    object Placeholder3 : NavRoutes("placeholder3")
    object Placeholder4 : NavRoutes("placeholder4")
    object Placeholder5 : NavRoutes("placeholder5")
    object Placeholder6 : NavRoutes("placeholder6")

    companion object {
        fun fromRoute(route: String?): NavRoutes {
            return when(route?.substringBefore("/")) {
                "training" -> Training
                "settings" -> Settings
                "placeholder1" -> Placeholder1
                "placeholder2" -> Placeholder2
                "placeholder3" -> Placeholder3
                "placeholder4" -> Placeholder4
                "placeholder5" -> Placeholder5
                "placeholder6" -> Placeholder6
                null -> Training
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
        }
    }
}

// Navigation items for bottom navigation
enum class NavigationItem(
    val route: String,
    val icon: String, // We'll use string resources for icons
    val labelResId: String // We'll use string resources for labels
) {
    TRAINING(NavRoutes.Training.route, "fitness_center", "nav_training"),
    SETTINGS(NavRoutes.Settings.route, "settings", "nav_settings")
}
