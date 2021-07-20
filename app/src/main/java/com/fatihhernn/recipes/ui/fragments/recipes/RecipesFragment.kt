package com.fatihhernn.recipes.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatihhernn.recipes.viewmodels.MainViewModel
import com.fatihhernn.recipes.adapters.RecipesAdapter
import com.fatihhernn.recipes.databinding.FragmentRecipesBinding
import com.fatihhernn.recipes.util.Constants.Companion.API_KEY
import com.fatihhernn.recipes.util.NetworkResult
import com.fatihhernn.recipes.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel:RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter() }

    private var _binding:FragmentRecipesBinding?=null
    private val binding get() = _binding!!

    private lateinit var mView:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel=ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater,container,false)

        setupRecyclerView()
        requestApiData()

        return binding.root
    }

    private fun requestApiData(){
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner,{response->
            when(response){
                is NetworkResult.Success->{
                    hideShimmerEffect()
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Error->{
                    hideShimmerEffect()
                    Toast.makeText(requireContext(),response.message.toString(),Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading->{
                    showShimmerEffect()
                }
            }
        })
    }
    private fun showShimmerEffect(){
        _binding!!.recylerview.showShimmer()
    }
    private fun hideShimmerEffect(){
        _binding!!.recylerview.hideShimmer()
    }
    private fun setupRecyclerView(){
        _binding!!.recylerview.adapter=mAdapter
        _binding!!.recylerview.layoutManager=LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

}