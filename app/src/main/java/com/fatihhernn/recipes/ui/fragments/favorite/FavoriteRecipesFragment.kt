package com.fatihhernn.recipes.ui.fragments.favorite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.adapters.FavoriteRecipesAdapter
import com.fatihhernn.recipes.databinding.FragmentFavoriteRecipesBinding
import com.fatihhernn.recipes.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter(requireActivity(),mainViewModel) }

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

        setHasOptionsMenu(true)

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
        mAdapter.clearContextualActionMode()
        _binding=null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.deleteAll_favorite_recipes_menu){
            mainViewModel.deleteAllFavoriteRecipes()
            showSnackBar("All recipes removed")
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showSnackBar(message:String){
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).setAction("Okay"){}.show()
    }

}