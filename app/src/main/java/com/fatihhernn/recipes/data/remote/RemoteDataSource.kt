package com.fatihhernn.recipes.data.remote

import com.fatihhernn.recipes.data.network.AuthApiService
import com.fatihhernn.recipes.data.network.FoodRecipesApi
import com.fatihhernn.recipes.models.FoodJoke
import com.fatihhernn.recipes.models.FoodRecipe
import com.fatihhernn.recipes.models.login.LoginRequest
import com.fatihhernn.recipes.models.register.RegisterRequest
import com.fatihhernn.recipes.util.BaseDataSource
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi,
    private val authApi: AuthApiService
) : BaseDataSource(){

    suspend fun postLogin(request: LoginRequest)=getResult { authApi.login(request) }

    suspend fun postRegister(request: RegisterRequest)=getResult { authApi.register(request) }

    suspend fun getRecipes(queries:Map<String,String>
    ): Response<FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery:Map<String,String>
    ): Response<FoodRecipe>{
        return foodRecipesApi.searchRecipes(searchQuery)
    }

    suspend fun getFoodJoke(apiKey:String):Response<FoodJoke>{
        return foodRecipesApi.getFoodJoke(apiKey)
    }

}