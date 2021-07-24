package com.fatihhernn.recipes.ui.fragments.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.adapters.FavoriteRecipesAdapter
import com.fatihhernn.recipes.databinding.FragmentFavoriteRecipesBinding
import com.fatihhernn.recipes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private val mAdapter: FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter(requireActivity()) }
    private val mainViewModel: MainViewModel by viewModels()

    private var _binding:FragmentFavoriteRecipesBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentFavoriteRecipesBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.mainViewModel=mainViewModel
        binding.mAdapter = mAdapter

        setUpRecyclerView(binding.favoriteRecipesRecyclerView)

        /** Implemented in bindingAdapters->FavoriteRecipeBinding - if adapter is empty data it controls
        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner, { favoritesEntity ->
            mAdapter.setData(favoritesEntity)
        })
         */

        return binding.root
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}