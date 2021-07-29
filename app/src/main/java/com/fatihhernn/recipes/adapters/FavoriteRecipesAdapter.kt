package com.fatihhernn.recipes.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.data.database.entities.FavoritesEntity
import com.fatihhernn.recipes.databinding.FavoriteRecipesRowLayoutBinding
import com.fatihhernn.recipes.ui.fragments.favorite.FavoriteRecipesFragmentDirections
import com.fatihhernn.recipes.util.RecipesDiffUtil

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(),
    ActionMode.Callback {

    private var favoriteRecipes = emptyList<FavoritesEntity>()

    private lateinit var mActionMode:ActionMode

    private var multiSelection=false
    private var myViewHolders= arrayListOf<MyViewHolder>()
    private var selectedRecipes= arrayListOf<FavoritesEntity>()

    class MyViewHolder(val binding: FavoriteRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder.from(parent)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        myViewHolders.add(holder)

        val currentRecipe = favoriteRecipes[position]
        holder.bind(currentRecipe)

        /**
         * .. Single Click Listener
         * */
        holder.binding.favoriteRecipesRowLayout.setOnClickListener {
            if (multiSelection){
                applySelection(holder,currentRecipe)
            }else{
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                        favoriteRecipes[position].result
                    )
                holder.itemView.findNavController().navigate(action)
            }

        }

        /**
         * .. Long Click Listener
         * */
        holder.binding.favoriteRecipesRowLayout.setOnLongClickListener {
            if (!multiSelection){
                multiSelection=true
                requireActivity.startActionMode(this)
                applySelection(holder,currentRecipe)
                true
            }else{
                multiSelection=false
                false
            }

        }
    }

    override fun getItemCount(): Int = favoriteRecipes.size

    private fun applySelection(holder:MyViewHolder,currenRecipe:FavoritesEntity){
        if (selectedRecipes.contains(currenRecipe)){
            selectedRecipes.remove(currenRecipe)
            changeRecipeStyle(holder,R.color.cardBackgroundColor,R.color.strokeColor)
            applyActionModeTitle()

        }else{
            selectedRecipes.add(currenRecipe)
            changeRecipeStyle(holder,R.color.cardBackgroundLightColor,R.color.colorPrimary)
            applyActionModeTitle()
        }
    }
    private fun changeRecipeStyle(holder:MyViewHolder,backgroundColor:Int,strokeColor: Int){
        holder.binding.favoriteRecipesRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity,backgroundColor)
        )
        holder.binding.favoriteRowCardView.strokeColor=ContextCompat.getColor(requireActivity,strokeColor)
    }

    private fun applyActionModeTitle(){
        when(selectedRecipes.size){
            0->mActionMode.finish()
            1->mActionMode.title="${selectedRecipes.size} item selected"
            else->mActionMode.title="${selectedRecipes.size} items selected"
        }
    }
    private fun applyStatusBarColor(color:Int){
        requireActivity.window.statusBarColor=ContextCompat.getColor(requireActivity,color)
    }

    override fun onCreateActionMode(actionMode: ActionMode, menu: Menu?): Boolean {
        actionMode.menuInflater.inflate(R.menu.favorites_contextual_menu, menu)
        mActionMode=actionMode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {

        myViewHolders.forEach { myViewHolder ->
            changeRecipeStyle(myViewHolder,R.color.cardBackgroundColor,R.color.strokeColor)
        }
        multiSelection=false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffUtil = RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }
}