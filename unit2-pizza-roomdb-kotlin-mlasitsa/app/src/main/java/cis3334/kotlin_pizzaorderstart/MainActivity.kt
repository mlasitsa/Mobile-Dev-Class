package cis3334.kotlin_pizzaorderstart

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme { Surface(Modifier.fillMaxSize()) { PizzaOrderScreen() } }
        }
    }
}

@Composable
fun PizzaOrderScreen(vm: MainViewModel = viewModel()) {
    val context = LocalContext.current

    val size = vm.size
    val chicken = vm.chicken
    val pepperoni = vm.pepperoni
    val greenPeppers = vm.greenPeppers
    val orderText = vm.orderText

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Pizza Order", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))

        Column(Modifier.fillMaxWidth()) {
            Text("Size: ${size.toPretty()}", style = MaterialTheme.typography.titleMedium)
            Slider(
                value = size.toIndex().toFloat(),
                onValueChange = { vm.setSizeIndex(it.toInt()) },
                valueRange = 0f..3f,
                steps = 2
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Small")
                Text("X-Large")
            }
        }

        Spacer(Modifier.height(16.dp))

        Column(Modifier.fillMaxWidth()) {
            Text("Toppings", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip(selected = chicken, onClick = { vm.toggleChicken() }, label = { Text("Chicken") })
                FilterChip(selected = pepperoni, onClick = { vm.togglePepperoni() }, label = { Text("Pepperoni") })
                FilterChip(selected = greenPeppers, onClick = { vm.toggleGreenPeppers() }, label = { Text("Green Peppers") })
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(modifier = Modifier.weight(1f), onClick = { vm.addToOrder() }) { Text("Add To Order") }
            Button(modifier = Modifier.weight(1f), onClick = {
                Toast.makeText(context, "Order placed!", Toast.LENGTH_SHORT).show()
            }) { Text("Place Order") }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = orderText,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            label = { Text("Order") },
            placeholder = { Text("Your order will appear here...") },
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Text),
            readOnly = true,
            minLines = 6,
            maxLines = 20
        )
    }
}

private fun PizzaSize.toPretty(): String = when (this) {
    PizzaSize.SMALL -> "Small"
    PizzaSize.MEDIUM -> "Medium"
    PizzaSize.LARGE -> "Large"
    PizzaSize.XLARGE -> "X-Large"
}

private fun PizzaSize.toIndex(): Int = when (this) {
    PizzaSize.SMALL -> 0
    PizzaSize.MEDIUM -> 1
    PizzaSize.LARGE -> 2
    PizzaSize.XLARGE -> 3
}

@Preview(showBackground = true)
@Composable
private fun PizzaWithVmPreview() {
    val fakeVm = remember { MainViewModel(android.app.Application()) }
    MaterialTheme { PizzaOrderScreen(vm = fakeVm) }
}
