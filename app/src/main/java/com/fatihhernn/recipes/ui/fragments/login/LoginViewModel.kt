package com.fatihhernn.recipes.ui.fragments.login

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatihhernn.recipes.data.Repository
import com.fatihhernn.recipes.models.login.LoginRequest
import com.fatihhernn.recipes.models.login.LoginResponse
import com.fatihhernn.recipes.util.Resource


class LoginViewModel  @ViewModelInject constructor(
    private val repository: Repository,
    application: Application,
) : AndroidViewModel(application) {

    private val loginService= MutableLiveData<Resource<LoginResponse>>()

    fun observeNavigationLiveData(): LiveData<Resource<LoginResponse>> =loginService

    fun login(mail: String, password: String) : LiveData<Resource<LoginResponse>> {
        val request= LoginRequest(mail,password)
        return repository.login(request)
    }
}