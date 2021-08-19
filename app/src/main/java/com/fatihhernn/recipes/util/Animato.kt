package com.fatihhernn.recipes.util

import android.app.Activity
import android.content.Context
import com.fatihhernn.recipes.R


object Animato {
    fun animateDiagonal(context: Context) {
        (context as Activity).overridePendingTransition(
            R.anim.animate_diagonal_right_enter,
            R.anim.animate_diagonal_right_exit
        )
    }

}