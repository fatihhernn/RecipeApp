package com.fatihhernn.recipes.models.login

import com.fatihhernn.recipes.models.common.Data
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String
)