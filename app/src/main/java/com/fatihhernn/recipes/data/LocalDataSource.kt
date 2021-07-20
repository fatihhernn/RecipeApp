package com.fatihhernn.recipes.data

import com.fatihhernn.recipes.data.database.RecipesDao
import com.fatihhernn.recipes.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao:RecipesDao
) {
    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }
    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        return recipesDao.insertRecipes(recipesEntity)
    }
}