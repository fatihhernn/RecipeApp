package com.fatihhernn.recipes.models.profile

import com.fatihhernn.recipes.models.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val user: User,
    @SerializedName("success")
    val success: Boolean
)