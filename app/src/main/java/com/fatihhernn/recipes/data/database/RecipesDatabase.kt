package com.fatihhernn.recipes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fatihhernn.recipes.data.database.entities.FavoritesEntity
import com.fatihhernn.recipes.data.database.entities.FoodJokeEntity
import com.fatihhernn.recipes.data.database.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class,FavoritesEntity::class,FoodJokeEntity::class],
    version = 3,
    exportSchema = false
)


@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase:RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}