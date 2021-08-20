package com.fatihhernn.recipes.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.adapters.PagerAdapter
import com.fatihhernn.recipes.data.database.entities.FavoritesEntity
import com.fatihhernn.recipes.databinding.ActivityDetailBinding
import com.fatihhernn.recipes.ui.fragments.ingredients.IngredientsFragment
import com.fatihhernn.recipes.ui.fragments.instructions.InstructionsFragment
import com.fatihhernn.recipes.ui.fragments.overview.OverviewFragment
import com.fatihhernn.recipes.util.Constants.Companion.RECIPE_RESULT_KEY
import com.fatihhernn.recipes.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val args by navArgs<DetailActivityArgs>()

    /**Initialize Main ViewModel*/
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved = false
    private var savedRecipeId = 0

    private lateinit var menuItem:MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolBar)
        binding.toolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this)

        binding.viewPager2.apply {
            adapter=pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout,binding.viewPager2) {tab,position->
            tab.text=titles[position]
        }.attach()

    }

    /** Favorite Icon */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        menuItem = menu!!.findItem(R.id.save_to_favorites_menu)
        checkSavedRecipes(menuItem)
        return true
    }


    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this, { favoriteEntity ->
            try {
                for (savedRecipe in favoriteEntity) {
                    if (savedRecipe.result.id == args.result.id) {
                        changeMenuItemColors(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.toString())
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favorites_menu && !recipeSaved) {
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_to_favorites_menu && recipeSaved) {
            removeFromFavorite(item)
        }
        else if (item.itemId == R.id.share_recipe ) {
            val shareIntent= Intent().apply {
                this.action= Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT,Jsoup.parse(args.result.summary).text())
                this.type="text/plain"
            }
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(0, args.result)
        mainViewModel.insertFavoriteRecipe(favoritesEntity)
        changeMenuItemColors(item, R.color.yellow)
        showSnackBar("Recipe Saved")
        recipeSaved = true
    }

    private fun removeFromFavorite(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(savedRecipeId, args.result)
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        changeMenuItemColors(item, R.color.white)
        showSnackBar("Removed from Favorites..")
        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            findViewById(R.id.detailsLayout),
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}.show()
    }

    private fun changeMenuItemColors(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColors(menuItem,R.color.white)
    }


}