package com.fatihhernn.recipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatihhernn.recipes.databinding.RecipesRowLayoutBinding
import com.fatihhernn.recipes.models.FoodRecipe
import com.fatihhernn.recipes.models.Result
import com.fatihhernn.recipes.util.RecipesDiffUtil


class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipe= emptyList<Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(result: Result){
                binding.result=result
                binding.executePendingBindings()
            }

        companion object{
            fun from(parent:ViewGroup):MyViewHolder{
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=RecipesRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult=recipe[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int =recipe.size

    fun setData(newData:FoodRecipe){
        val recipesDiffUtil=RecipesDiffUtil(recipe,newData.results)
        val diffUtilResult=DiffUtil.calculateDiff(recipesDiffUtil)
        recipe=newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}