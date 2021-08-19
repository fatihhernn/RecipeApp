package com.fatihhernn.recipes.data.database.sharedPref

import javax.inject.Inject

class SharedLocalDataSource @Inject constructor (val sharedPrefManager: SharedPrefManager) {
    fun saveToken(token:String){
        sharedPrefManager.saveToken(token)
    }
    fun getToken():String{
        return sharedPrefManager.getToken()!!
    }
}