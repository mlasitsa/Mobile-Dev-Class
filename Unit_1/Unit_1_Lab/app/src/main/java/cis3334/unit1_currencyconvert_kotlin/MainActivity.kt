package cis3334.unit1_currencyconvert_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// --- Very simple fixed-rate converter for an intro class ---
private const val USD_TO_EUR = 0.92 // change this constant as needed

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CurrencyConverterScreen()
                }
            }
        }
    }
}

@Composable
fun CurrencyConverterScreen() {
    var dollars by remember { mutableStateOf("") }
    var euros by remember { mutableStateOf("") }

    Column() {
        Text(text = "Currency Converter", style = MaterialTheme.typography.headlineMedium)

        // Two side-by-side columns
        Row() {
            // Dollars column
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Dollars", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = dollars,
                    onValueChange = { dollars = it },
                    placeholder = { Text("Dollars") },
                )
                Button(onClick = {
                    val d = dollars.toDoubleOrNull()
                    euros = if (d != null) formatTwo(d * USD_TO_EUR) else ""
                }) {
                    Text("TO EUROS")
                }

            }

            // Euros column
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Euros", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = euros,
                    onValueChange = { euros = it },
                    placeholder = { Text("Euros") },
                )
                Button(onClick = {
                    val e = euros.toDoubleOrNull()
                    dollars = if (e != null) formatTwo(e / USD_TO_EUR) else ""
                }) {
                    Text("TO DOLLARS")
                }
            }

        }
    }
}

private fun formatTwo(n: Double): String = String.format("%.2f", n)

@Preview(showBackground = true)
@Composable
fun PreviewCurrencyConverter() {
    MaterialTheme { CurrencyConverterScreen() }
}
