package com.fatihhernn.recipes.data

import com.fatihhernn.recipes.data.network.FoodRecipesApi
import com.fatihhernn.recipes.models.FoodRecipe
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