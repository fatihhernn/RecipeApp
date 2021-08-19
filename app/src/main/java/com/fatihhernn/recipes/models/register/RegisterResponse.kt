package com.fatihhernn.recipes.models.register

import com.fatihhernn.recipes.models.common.Data
import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String
) {
}