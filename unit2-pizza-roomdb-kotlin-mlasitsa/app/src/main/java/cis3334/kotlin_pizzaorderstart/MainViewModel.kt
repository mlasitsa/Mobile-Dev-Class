package cis3334.kotlin_pizzaorderstart

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


// TEst


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PizzaRepository(application)

    var pizzas: List<Pizza> by mutableStateOf(emptyList())
        private set

    var size by mutableStateOf(PizzaSize.MEDIUM)
        private set
    var chicken by mutableStateOf(false)
        private set
    var pepperoni by mutableStateOf(false)
        private set
    var greenPeppers by mutableStateOf(false)
        private set
    var orderText by mutableStateOf("")
        private set

    init {
        // Observe repository LiveData and update Compose state
        repository.pizzas.observeForever { list ->
            pizzas = list
            orderText = pizzas.joinToString("\n") { it.description() }
        }
    }

    fun setSizeIndex(index: Int) {
        size = when (index.coerceIn(0, 3)) {
            0 -> PizzaSize.SMALL
            1 -> PizzaSize.MEDIUM
            2 -> PizzaSize.LARGE
            else -> PizzaSize.XLARGE
        }
    }

    fun toggleChicken() { chicken = !chicken }
    fun togglePepperoni() { pepperoni = !pepperoni }
    fun toggleGreenPeppers() { greenPeppers = !greenPeppers }

    fun addToOrder() {
        val tops = buildSet {
            if (chicken) add(Topping.CHICKEN)
            if (pepperoni) add(Topping.PEPPERONI)
            if (greenPeppers) add(Topping.GREEN_PEPPERS)
        }
        val pizza = Pizza(size = size, toppings = tops)
        repository.insert(pizza)
        resetSelectionsForNext()
    }

    private fun resetSelectionsForNext() {
        chicken = false
        pepperoni = false
        greenPeppers = false
    }
}
