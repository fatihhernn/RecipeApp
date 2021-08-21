package com.fatihhernn.recipes.data.network

import com.fatihhernn.recipes.models.User
import com.fatihhernn.recipes.models.profile.UserRequest
import com.fatihhernn.recipes.models.profile.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface AuthorizationApiService {
    @PUT("auth/updateDetails")
    suspend fun updateUser(@Body request : UserRequest) : Response<User>

    @GET("auth/profile")
    suspend fun getUser() : Response<UserResponse>
}