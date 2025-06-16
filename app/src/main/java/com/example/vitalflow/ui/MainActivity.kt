package com.example.vitalflow.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vitalflow.ui.navigation.AppNavigation
import com.example.vitalflow.ui.navigation.NavRoutes
import com.example.vitalflow.ui.navigation.NavigationItem
import com.example.vitalflow.ui.theme.VitalFlowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VitalFlowTheme {
                VitalFlowApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalFlowApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationItem.values().forEach { item ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { 
                                it.route?.startsWith(item.route) == true 
                            } == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        id = context.resources.getIdentifier(
                                            item.icon,
                                            "drawable",
                                            context.packageName
                                        )
                                    ),
                                    contentDescription = stringResource(id = item.labelResId.toInt())
                                )
                            },
                            label = {
                                Text(stringResource(id = item.labelResId.toInt()))
                            }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AppNavigation(navController = navController)
            }
        }
    }
}
