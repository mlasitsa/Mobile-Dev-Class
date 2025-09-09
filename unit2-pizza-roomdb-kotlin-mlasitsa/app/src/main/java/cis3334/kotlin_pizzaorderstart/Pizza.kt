package cis3334.kotlin_pizzaorderstart

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

enum class PizzaSize { SMALL, MEDIUM, LARGE, XLARGE }
enum class Topping { CHICKEN, PEPPERONI, GREEN_PEPPERS }

@Entity
@TypeConverters(ToppingConverter::class)
data class Pizza(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val size: PizzaSize = PizzaSize.MEDIUM,
    val toppings: Set<Topping> = emptySet()
) {
    fun description(): String {
        val sizeText = when (size) {
            PizzaSize.SMALL -> "Small"
            PizzaSize.MEDIUM -> "Medium"
            PizzaSize.LARGE -> "Large"
            PizzaSize.XLARGE -> "X-Large"
        }
        val toppingText = if (toppings.isEmpty()) "Cheese" else toppings
            .map {
                when (it) {
                    Topping.CHICKEN -> "Chicken"
                    Topping.PEPPERONI -> "Pepperoni"
                    Topping.GREEN_PEPPERS -> "Green Peppers"
                }
            }
            .joinToString(", ")
        return "$sizeText pizza with $toppingText"
    }
}
