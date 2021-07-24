package com.fatihhernn.recipes.data

import com.fatihhernn.recipes.data.database.RecipesDao
import com.fatihhernn.recipes.data.database.entities.FavoritesEntity
import com.fatihhernn.recipes.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao:RecipesDao
) {
    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }
    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        return recipesDao.insertRecipes(recipesEntity)
    }


    fun readFavoriteRecipes():Flow<List<FavoritesEntity>>{
        return recipesDao.readFavoriteRecipes()
    }
    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity){
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }
    suspend fun deleteFavoriteRecipes(favoritesEntity: FavoritesEntity){
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }
    suspend fun deleteAllFavoriteRecipes(){
        recipesDao.deleteAllFavoriteRecipes()
    }
}