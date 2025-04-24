package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrencyConverterScreen()
                }
            }
        }
    }
}

@Composable
fun CurrencyConverterScreen() {
    var idrAmount by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf("USD") }

    val conversionRates = mapOf(
        "USD" to 0.000059,
        "GBP" to 0.000045,
        "EUR" to 0.00005225239,
        "JPY" to 0.0084
    )

    val convertedAmount = idrAmount.toDoubleOrNull()?.let {
        it * (conversionRates[selectedCurrency] ?: 0.0)
    } ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Currency Converter", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = idrAmount,
            onValueChange = { idrAmount = it },
            label = { Text("Amount in IDR") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        CurrencyDropdown(
            selectedCurrency = selectedCurrency,
            onCurrencySelected = { selectedCurrency = it }
        )

        Text(
            text = "Converted: $convertedAmount $selectedCurrency",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun CurrencyDropdown(selectedCurrency: String, onCurrencySelected: (String) -> Unit) {
    val currencies = listOf("USD", "GBP", "EUR", "JPY")
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedCurrency)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(currency) },
                    onClick = {
                        onCurrencySelected(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}
