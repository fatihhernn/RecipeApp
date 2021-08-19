package com.fatihhernn.recipes.data.network

import com.fatihhernn.recipes.models.login.LoginRequest
import com.fatihhernn.recipes.models.login.LoginResponse
import com.fatihhernn.recipes.models.register.RegisterRequest
import com.fatihhernn.recipes.models.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest):Response<LoginResponse>
}
