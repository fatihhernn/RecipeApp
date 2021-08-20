package com.fatihhernn.recipes.ui.fragments.overview

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import coil.load
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.databinding.FragmentOverviewBinding
import com.fatihhernn.recipes.models.Result
import com.fatihhernn.recipes.util.Constants.Companion.RECIPE_RESULT_KEY
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private var summary=""

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args=arguments
        val myBundle:Result ? =args?.getParcelable(RECIPE_RESULT_KEY)

        binding.mainImageView.load(myBundle?.image)
        binding.titleTextView.text=myBundle?.title
        binding.likesTextView.text=myBundle?.aggregateLikes.toString()
        binding.timeTextView.text=myBundle?.readyInMinutes.toString()
        //binding.summaryTextView.text=myBundle?.summary.toString()
        myBundle?.summary.let {
            summary=Jsoup.parse(it).text()
            binding.summaryTextView.text=summary
        }

        if (myBundle?.vegetarian==true){
            binding.vegeterianImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.vegetarianTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if (myBundle?.vegan==true){
            binding.veganImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.veganTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if (myBundle?.glutenFree==true){
            binding.glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if (myBundle?.dairyFree==true){
            binding.dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if (myBundle?.veryHealthy==true){
            binding.healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.healthyTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if (myBundle?.cheap==true){
            binding.cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.cheapTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }


        return binding.root
    }
}