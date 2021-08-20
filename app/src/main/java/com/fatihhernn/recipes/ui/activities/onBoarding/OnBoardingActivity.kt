package com.fatihhernn.recipes.ui.activities.onBoarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.fatihhernn.recipes.models.onBoarding.OnBoardingData
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.data.database.sharedPref.SharedPrefManager
import com.fatihhernn.recipes.databinding.ActivityOnBoardingStartBinding
import com.fatihhernn.recipes.ui.activities.AuthActivity
import com.fatihhernn.recipes.util.Animato
import com.fatihhernn.recipes.util.ZoomOutPageTransformer
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingStartBinding
    private lateinit var viewPager2: ViewPager2

    private var itemList=ArrayList<OnBoardingData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide();

        //val token=getToken()

        if(isOnboardingSeen()) {
            val intent = Intent(applicationContext, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(R.layout.activity_on_boarding_start)

        binding= ActivityOnBoardingStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        initViewPager()

        //skip butonuna basılınca GettinStarting activity'e giden bir listener
        binding.txtSkip.setOnClickListener {
            finish()
            val intent =
                Intent(applicationContext, AuthActivity::class.java)
            startActivity(intent)
            Animato.animateDiagonal(this)
        }
        binding.txtNext.setOnClickListener {
            if (currentGetItem() > viewPager2.childCount) {

                val intent = Intent(applicationContext, AuthActivity::class.java)
                startActivity(intent)
                finish()

                Animato.animateDiagonal(this)
            } else {
                viewPager2.setCurrentItem(currentGetItem() + 1, true)
            }
        }
    }
    fun onClick(v:View){
        when(v){
            binding.imgPrevious->{
                val currentItemPosition=binding.viewPager.currentItem
                if (currentItemPosition==0) return
                binding.viewPager.setCurrentItem(currentItemPosition-1,true)
            }
            binding.txtNext->{
                val currentItemPosition=binding.viewPager.currentItem
                if (currentItemPosition==itemList.size-1) {
                    Toast.makeText(this,"Start Next Process", Toast.LENGTH_SHORT).show()
                    return
                }
                binding.viewPager.setCurrentItem(currentItemPosition+1,true)
            }
            binding.txtSkip->{
                Toast.makeText(this,"Start Next Process", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews() {
        viewPager2 = findViewById(R.id.viewPager)
    }
    private fun initViewPager() {
        itemList=getItems()
        val wormDotsIndicator = findViewById<WormDotsIndicator>(R.id.worm_dot)
        val adapter = ViewPagerAdapter(this,itemList)
        viewPager2.adapter = adapter


        //pagerda dahil olan fragment geçişlerine eklenen animasyon yönetimi
        val zoomOutPageTransformer = ZoomOutPageTransformer()
        viewPager2.setPageTransformer { page, position ->
            zoomOutPageTransformer.transformPage(page, position)
        }

        setOnBoardingSeen()

        //indicator yönetimi
        wormDotsIndicator.setViewPager2(viewPager2)
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback)
    }
    private fun currentGetItem():Int{
        return viewPager2.currentItem
    }

    private var pageChangeCallback:ViewPager2.OnPageChangeCallback= object: ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.imgPrevious.visibility= View.INVISIBLE.takeIf { position==0 }?: View.VISIBLE

            if (position==itemList.size-1){
                binding.txtNext.text="Start"
                binding.txtSkip.visibility= View.GONE
            }
            else{
                binding.txtNext.text="Next"
                binding.txtSkip.visibility= View.VISIBLE
            }
        }
    }

    private fun getItems():ArrayList<OnBoardingData>{
        val items=ArrayList<OnBoardingData>()
        items.add(
            OnBoardingData(
                "Yummy Recipes are here",
                "From easy and practical recipes, you can easily access different recipes that not everyone knows.",
                R.drawable.ic_orderfood
            )
        )
        items.add(
            OnBoardingData(
                "Learn Examples from World Cuisine",
                "From the most popular dishes to the newest, simple and beautiful recipes, illustrated and tried all delicious recipes, detailed explanations",
                R.drawable.ic_safecooking
            )
        )
        items.add(
            OnBoardingData(
                "Instant Access to Thousands of Tariffs",
                "Recipe page where you can find video recipes, ingredient lists and tips",
                R.drawable.ic_deliveryfood
            )
        )
        return items
    }



    private fun setOnBoardingSeen(){
         SharedPrefManager(applicationContext).setOnboardingSeen()
    }

    private fun isOnboardingSeen(): Boolean {
        return SharedPrefManager(applicationContext).isOnboardingSeen()
    }
}