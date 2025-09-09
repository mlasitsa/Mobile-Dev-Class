package cis3334.kotlin_pizzaorderstart

// --- File: PizzaOrder.kt ---

data class PizzaOrder(
    val items: MutableList<Pizza> = mutableListOf()
) {
    fun add(pizza: Pizza) { items += pizza }
    fun clear() { items.clear() }
    fun count(): Int = items.size
    fun asText(): String = items.joinToString(separator = "\n") { it.description() }
}