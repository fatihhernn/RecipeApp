package com.fatihhernn.recipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.adapters.PagerAdapter
import com.fatihhernn.recipes.databinding.ActivityDetailBinding
import com.fatihhernn.recipes.ui.fragments.ingredients.IngredientsFragment
import com.fatihhernn.recipes.ui.fragments.instructions.InstructionsFragment
import com.fatihhernn.recipes.ui.fragments.overview.OverviewFragment
import com.fatihhernn.recipes.util.Constants.Companion.RECIPE_RESULT_KEY

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val args by navArgs<DetailActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolBar)
        binding.toolBar.setTitleTextColor(ContextCompat.getColor(this,R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments=ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles=ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle=Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY,args.result)

        val adapter=PagerAdapter(resultBundle,fragments,titles,supportFragmentManager)
        binding.viewPager.adapter=adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}