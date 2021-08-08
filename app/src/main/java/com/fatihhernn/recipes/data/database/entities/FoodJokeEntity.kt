package com.fatihhernn.recipes.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fatihhernn.recipes.models.FoodJoke
import com.fatihhernn.recipes.util.Constants.Companion.FOOD_JOKE_TABLE

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
    ) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}