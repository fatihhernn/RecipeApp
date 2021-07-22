package com.fatihhernn.recipes.data.network

import com.fatihhernn.recipes.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {
//https://api.spoonacular.com/recipes/complexSearch?number=50&apiKey=5f4fae60dba24eee90ffcb754c28259a&type=snack&diet=vegan&addRecipeInformation=true&fillIngredients=true

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries:Map<String,String>
    ):Response<FoodRecipe>


    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String,String>
    ):Response<FoodRecipe>
}