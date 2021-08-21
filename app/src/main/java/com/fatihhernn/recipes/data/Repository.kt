package com.fatihhernn.recipes.data

import com.fatihhernn.recipes.data.database.sharedPref.SharedLocalDataSource
import com.fatihhernn.recipes.data.remote.AuthorizationRemoteDataSource
import com.fatihhernn.recipes.data.remote.RemoteDataSource
import com.fatihhernn.recipes.models.login.LoginRequest
import com.fatihhernn.recipes.models.register.RegisterRequest
import com.fatihhernn.recipes.util.performAuthTokenNetworkOperation
import com.fatihhernn.recipes.util.performNetworkOperation
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val sharedLocalDataSource: SharedLocalDataSource,
    private var authorizationRemoteDataSource: AuthorizationRemoteDataSource

) {
    val remote=remoteDataSource
    val local=localDataSource

    fun register(registerRequest: RegisterRequest)= performNetworkOperation {
        remoteDataSource.postRegister(registerRequest)
    }

    fun login(loginRequest: LoginRequest)= performAuthTokenNetworkOperation(
        call = {
            remoteDataSource.postLogin(loginRequest)},
        saveToken = {
            sharedLocalDataSource.saveToken(it)
        }
    )
    fun checkToken():String?{
        val token= sharedLocalDataSource.getToken()
        return token
    }
    fun logOut() {
        sharedLocalDataSource.saveToken("")
    }
    fun getUser() = performNetworkOperation {
        authorizationRemoteDataSource.getUser()
    }
}