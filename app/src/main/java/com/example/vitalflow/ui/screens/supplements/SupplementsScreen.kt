package com.example.vitalflow.ui.screens.supplements

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplementsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var supplements by remember { mutableStateOf(sampleSupplements) }
    var selectedSupplement by remember { mutableStateOf<Supplement?>(null) }

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
                text = stringResource(R.string.supplements_tracking),
                style = AeroTheme.typography.headlineMedium
            )
            
            OutlinedButton(
                onClick = { showAddDialog = true }
            ) {
                Text(stringResource(R.string.supplements_add))
            }
        }

        // Supplements List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(supplements) { supplement ->
                SupplementCard(
                    supplement = supplement,
                    onClick = { selectedSupplement = supplement },
                    onDelete = { 
                        supplements = supplements.filter { it != supplement }
                    }
                )
            }
        }
    }

    // Add Supplement Dialog
    if (showAddDialog) {
        AddSupplementDialog(
            onDismiss = { showAddDialog = false },
            onSave = { supplement ->
                supplements = supplements + supplement
                showAddDialog = false
            }
        )
    }

    // Edit Supplement Dialog
    if (selectedSupplement != null) {
        AddSupplementDialog(
            supplement = selectedSupplement,
            onDismiss = { selectedSupplement = null },
            onSave = { updatedSupplement ->
                supplements = supplements.map { 
                    if (it == selectedSupplement) updatedSupplement else it 
                }
                selectedSupplement = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddSupplementDialog(
    supplement: Supplement? = null,
    onDismiss: () -> Unit,
    onSave: (Supplement) -> Unit
) {
    var manufacturer by remember { mutableStateOf(supplement?.manufacturer ?: "") }
    var productName by remember { mutableStateOf(supplement?.productName ?: "") }
    var dosage by remember { mutableStateOf(supplement?.dosage?.toString() ?: "") }
    var intakeTime by remember { mutableStateOf(supplement?.intakeTime ?: "08:00") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(
                    if (supplement == null) R.string.supplements_add
                    else R.string.action_edit
                )
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = manufacturer,
                    onValueChange = { manufacturer = it },
                    label = { Text(stringResource(R.string.supplements_manufacturer)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text(stringResource(R.string.supplements_product)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = dosage,
                    onValueChange = { dosage = it },
                    label = { Text(stringResource(R.string.supplements_dosage)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = intakeTime,
                    onValueChange = { intakeTime = it },
                    label = { Text(stringResource(R.string.supplements_time)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        Supplement(
                            manufacturer = manufacturer,
                            productName = productName,
                            dosage = dosage.toIntOrNull() ?: 0,
                            intakeTime = intakeTime
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
private fun SupplementCard(
    supplement: Supplement,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    AeroCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = supplement.productName,
                    style = AeroTheme.typography.titleMedium
                )
                Text(
                    text = supplement.manufacturer,
                    style = AeroTheme.typography.bodyMedium,
                    color = AeroTheme.colors.onSurfaceVariant
                )
                Text(
                    text = "${supplement.dosage}mg at ${supplement.intakeTime}",
                    style = AeroTheme.typography.bodySmall,
                    color = AeroTheme.colors.onSurfaceVariant
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onClick
                ) {
                    Text(stringResource(R.string.action_edit))
                }
                OutlinedButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(stringResource(R.string.action_delete))
                }
            }
        }
    }
}

data class Supplement(
    val manufacturer: String,
    val productName: String,
    val dosage: Int, // in mg
    val intakeTime: String
)

// Sample supplements
private val sampleSupplements = listOf(
    Supplement(
        manufacturer = "VitaCorp",
        productName = "Vitamin D3",
        dosage = 1000,
        intakeTime = "08:00"
    ),
    Supplement(
        manufacturer = "ProteinPlus",
        productName = "Whey Protein",
        dosage = 30000,
        intakeTime = "Post-workout"
    ),
    Supplement(
        manufacturer = "OmegaLife",
        productName = "Fish Oil",
        dosage = 1200,
        intakeTime = "20:00"
    )
)

@Preview(showBackground = true)
@Composable
fun SupplementsScreenPreview() {
    MaterialTheme {
        SupplementsScreen(
            navController = rememberNavController()
        )
    }
}
