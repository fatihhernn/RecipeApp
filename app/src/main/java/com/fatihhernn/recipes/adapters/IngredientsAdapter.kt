package com.fatihhernn.recipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.models.ExtendedIngredient
import com.fatihhernn.recipes.util.Constants.Companion.BASE_IMAGE_URL
import com.fatihhernn.recipes.util.RecipesDiffUtil

class IngredientsAdapter:RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientList= emptyList<ExtendedIngredient>()

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.ingredient_imageView).load(BASE_IMAGE_URL+ingredientList[position].image){
            crossfade(600)
            error(R.drawable.error_placeholder)
        }
        holder.itemView.findViewById<TextView>(R.id.ingredient_name).text=ingredientList[position].name.uppercase()
        holder.itemView.findViewById<TextView>(R.id.ingredient_amount).text=ingredientList[position].amount.toString()
        holder.itemView.findViewById<TextView>(R.id.ingredientUnit_textView).text=ingredientList[position].unit
        holder.itemView.findViewById<TextView>(R.id.ingredientConsistency_textView).text=ingredientList[position].consistency
        holder.itemView.findViewById<TextView>(R.id.ingredientOriginal_textView).text=ingredientList[position].original
    }

    override fun getItemCount(): Int =ingredientList.size

    fun setData(newIngredients:List<ExtendedIngredient>){
        val ingredientsDiffUtil=RecipesDiffUtil(ingredientList,newIngredients)
        val diffUtilResult=DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientList=newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}