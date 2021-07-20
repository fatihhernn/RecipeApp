package com.fatihhernn.recipes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fatihhernn.recipes.models.FoodRecipe
import com.fatihhernn.recipes.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {

    @PrimaryKey(autoGenerate = false)
    var id :Int=0
}