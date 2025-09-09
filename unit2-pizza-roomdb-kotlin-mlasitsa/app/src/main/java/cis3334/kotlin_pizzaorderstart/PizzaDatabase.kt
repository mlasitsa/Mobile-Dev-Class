package cis3334.kotlin_pizzaorderstart

import android.content.Context
import androidx.room.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [Pizza::class], version = 1, exportSchema = false)
@TypeConverters(ToppingConverter::class)
abstract class PizzaDatabase : RoomDatabase() {

    abstract fun pizzaDao(): PizzaDao

    companion object {
        @Volatile
        private var INSTANCE: PizzaDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context): PizzaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PizzaDatabase::class.java,
                    "pizza_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
