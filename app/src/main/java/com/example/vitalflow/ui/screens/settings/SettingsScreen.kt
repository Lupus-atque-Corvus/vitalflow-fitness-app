package com.example.vitalflow.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vitalflow.R
import com.example.vitalflow.ui.components.AeroCard
import com.example.vitalflow.ui.navigation.NavRoutes
import com.example.vitalflow.utils.LanguageUtils
import com.example.vitalflow.utils.updateLocale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showLanguageDialog by remember { mutableStateOf(false) }
    var currentLanguage by remember { 
        mutableStateOf(LanguageUtils.getCurrentLanguage(context)) 
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Language Settings
        AeroCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.settings_language),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { showLanguageDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(currentLanguage.displayName)
                    )
                }
            }
        }

        // Placeholder Settings
        repeat(2) { index ->
            AeroCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(
                            R.string.placeholder_settings,
                            index + 1
                        ),
                        style = MaterialTheme.typography.titleMedium
                    )
                    OutlinedButton(
                        onClick = {
                            when (index) {
                                0 -> navController.navigate(NavRoutes.Settings.Placeholder1.route)
                                1 -> navController.navigate(NavRoutes.Settings.Placeholder2.route)
                            }
                        }
                    ) {
                        Text(stringResource(R.string.coming_soon))
                    }
                }
            }
        }
    }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = {
                Text(stringResource(R.string.settings_language))
            },
            text = {
                Column {
                    LanguageUtils.getSupportedLanguages().forEach { language ->
                        TextButton(
                            onClick = {
                                currentLanguage = language
                                (context as? android.app.Activity)?.updateLocale(language)
                                showLanguageDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(language.displayName),
                                color = if (currentLanguage == language) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showLanguageDialog = false }
                ) {
                    Text(stringResource(R.string.action_cancel))
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreen(
            navController = rememberNavController()
        )
    }
}
