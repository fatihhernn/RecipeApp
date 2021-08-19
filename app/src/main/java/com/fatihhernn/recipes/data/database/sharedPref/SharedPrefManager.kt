package com.fatihhernn.recipes.data.database.sharedPref

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context)  {
    companion object{
        const val TOKEN="com.fatihhernn.recipes.TOKEN"
        const val ONBOARDING="com.fatihhernn.recipes.ONBOARDING"
    }

    private val sharedPreferences : SharedPreferences =
        context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN,token).apply()
    }
    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN,"")
    }
    fun setOnboardingSeen() {
        sharedPreferences.edit().putBoolean(ONBOARDING, true).apply()
    }

    fun isOnboardingSeen(): Boolean {
        return sharedPreferences.getBoolean(ONBOARDING, false)
    }
}