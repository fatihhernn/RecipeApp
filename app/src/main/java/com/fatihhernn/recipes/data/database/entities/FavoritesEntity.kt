package com.fatihhernn.recipes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fatihhernn.recipes.models.Result
import com.fatihhernn.recipes.util.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result) {
}