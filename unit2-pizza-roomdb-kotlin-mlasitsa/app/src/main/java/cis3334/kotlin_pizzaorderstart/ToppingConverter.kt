package cis3334.kotlin_pizzaorderstart

import androidx.room.TypeConverter

class ToppingConverter {
    @TypeConverter
    fun fromToppings(toppings: Set<Topping>): String {
        return toppings.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toToppings(data: String): Set<Topping> {
        if (data.isEmpty()) return emptySet()
        return data.split(",").map { Topping.valueOf(it) }.toSet()
    }
}
