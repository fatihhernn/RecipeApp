package com.fatihhernn.recipes.ui.activities.onBoarding

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fatihhernn.recipes.ui.fragments.onBoarding.OrderFragment
import com.fatihhernn.recipes.ui.fragments.onBoarding.PrepareFragment
import com.fatihhernn.recipes.models.onBoarding.OnBoardingData
import com.fatihhernn.recipes.ui.fragments.onBoarding.DeliveryFragment
import com.fatihhernn.recipes.ui.fragments.onBoarding.EmptyFragment

class ViewPagerAdapter(activity: AppCompatActivity, private val items:ArrayList<OnBoardingData>):
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        val data =items[position]
        return when (position) {
            0 -> OrderFragment(OnBoardingData(data.title,data.description,data.image))
            1 -> PrepareFragment(OnBoardingData(data.title,data.description,data.image))
            2 -> DeliveryFragment(OnBoardingData(data.title,data.description,data.image))
            else -> EmptyFragment()
        }
    }
}