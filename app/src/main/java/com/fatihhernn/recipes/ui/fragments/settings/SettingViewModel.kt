package com.fatihhernn.recipes.ui.fragments.settings

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatihhernn.recipes.data.Repository
import com.fatihhernn.recipes.models.User
import com.fatihhernn.recipes.models.profile.UserRequest
import com.fatihhernn.recipes.models.profile.UserResponse
import com.fatihhernn.recipes.util.Resource

class SettingViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application,
) : AndroidViewModel(application)  {

    fun getUser(): LiveData<Resource<UserResponse>> = repository.getUser()

    fun updateUser(userRequest: UserRequest): LiveData<Resource<User>> =
        repository.updateUser(userRequest)

}