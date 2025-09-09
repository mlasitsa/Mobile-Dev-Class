package cis3334.kotlin_pizzaorderstart

import androidx.room.*

@Dao
interface PizzaDao {

    @Insert
    suspend fun insert(pizza: Pizza)

    @Delete
    suspend fun delete(pizza: Pizza)

    @Query("SELECT * FROM Pizza")
    suspend fun getAll(): List<Pizza>
}
