package com.fatihhernn.recipes.ui

import com.fatihhernn.recipes.models.FoodRecipe
import com.fatihhernn.recipes.ui.FoodRecipesApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries:Map<String,String>
    ): Response<FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)
    }

}