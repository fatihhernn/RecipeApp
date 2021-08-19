package com.fatihhernn.recipes.ui.fragments.register

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatihhernn.recipes.data.Repository
import com.fatihhernn.recipes.models.register.RegisterRequest
import com.fatihhernn.recipes.models.register.RegisterResponse
import com.fatihhernn.recipes.util.Resource

class RegisterViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application,
) : AndroidViewModel(application)  {

    fun register(email: String, userName: String, password: String) : LiveData<Resource<RegisterResponse>> {
        val request= RegisterRequest(email,userName,password)
        return repository.register(request)
    }

}