package com.fatihhernn.recipes.data.remote

import com.fatihhernn.recipes.data.network.AuthorizationApiService
import com.fatihhernn.recipes.models.profile.UserRequest
import com.fatihhernn.recipes.util.BaseDataSource
import javax.inject.Inject

class AuthorizationRemoteDataSource @Inject constructor(private val authorizationApiService: AuthorizationApiService) :
    BaseDataSource() {

    suspend fun updateUser(request : UserRequest) = getResult { authorizationApiService.updateUser(request) }

    suspend fun getUser() = getResult { authorizationApiService.getUser() }
}