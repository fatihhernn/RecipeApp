package com.fatihhernn.recipes.ui.fragments.splash

import android.app.Application
import android.os.Handler
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatihhernn.recipes.data.Repository


class SplashViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application,
) : AndroidViewModel(application)   {

    private val navigationLiveData= MutableLiveData<String>()
    fun observeNavigationLiveData(): LiveData<String> =navigationLiveData

    fun checkTokenAndNavigation() {
        Handler().postDelayed({
            if (repository.checkToken().isNullOrEmpty()){
                navigationLiveData.value="auth"
            }
            else
                navigationLiveData.value="home"

        },2000)
    }
}