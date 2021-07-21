package com.fatihhernn.recipes.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fatihhernn.recipes.data.DataStoreRepository
import com.fatihhernn.recipes.data.MealAndDietType
import com.fatihhernn.recipes.util.Constants
import com.fatihhernn.recipes.util.Constants.Companion.API_KEY
import com.fatihhernn.recipes.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.fatihhernn.recipes.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.fatihhernn.recipes.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.fatihhernn.recipes.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.fatihhernn.recipes.util.Constants.Companion.QUERY_API_KEY
import com.fatihhernn.recipes.util.Constants.Companion.QUERY_DIET
import com.fatihhernn.recipes.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.fatihhernn.recipes.util.Constants.Companion.QUERY_NUMBER
import com.fatihhernn.recipes.util.Constants.Companion.QUERY_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecipesViewModel @ViewModelInject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType= DEFAULT_MEAL_TYPE
    private var dietType= DEFAULT_DIET_TYPE

    val readMealAndDietType=dataStoreRepository.readMealAndMealType

    fun saveMealAndDietType(mealType:String,mealTypeId: Int,dietType: String,dietTypeId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType,mealTypeId, dietType, dietTypeId)
        }
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType=value.selectedMealType
                dietType=value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

}