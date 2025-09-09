package cis3334.kotlin_pizzaorderstart

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PizzaRepository(application: Application) {

    private val pizzaDao: PizzaDao
    private val _pizzas = MutableLiveData<List<Pizza>>(emptyList())
    val pizzas: LiveData<List<Pizza>> = _pizzas

    private val ioScope = CoroutineScope(Dispatchers.IO)

    init {
        val db = PizzaDatabase.getDatabase(application)
        pizzaDao = db.pizzaDao()
        refreshPizzas()
    }

    fun insert(pizza: Pizza) {
        ioScope.launch {
            pizzaDao.insert(pizza)
            refreshPizzas()
        }
    }

    private fun refreshPizzas() {
        ioScope.launch {
            val allPizzas = pizzaDao.getAll()
            _pizzas.postValue(allPizzas)
        }
    }
}
