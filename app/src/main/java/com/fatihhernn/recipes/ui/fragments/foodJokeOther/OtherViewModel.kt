package com.fatihhernn.recipes.ui.fragments.foodJokeOther

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatihhernn.recipes.data.Repository
import com.fatihhernn.recipes.models.profile.UserResponse
import com.fatihhernn.recipes.util.Resource

class OtherViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application,
) : AndroidViewModel(application)  {

    fun getUser(): LiveData<Resource<UserResponse>> = repository.getUser()
}