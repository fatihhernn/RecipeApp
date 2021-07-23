package com.fatihhernn.recipes.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.databinding.FragmentOverviewBinding
import com.fatihhernn.recipes.databinding.FragmentRecipesBinding
import com.fatihhernn.recipes.models.Result

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args=arguments
        val myBundle:Result ? =args?.getParcelable("recipeBundle")

        binding.mainImageView.load(myBundle?.image)
        binding.titleTextView.text=myBundle?.title
        binding.likesTextView.text=myBundle?.aggregateLikes.toString()
        binding.titleTextView.text=myBundle?.readyInMinutes.toString()
        binding.summaryTextView.text=myBundle?.summary.toString()

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

        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }
}