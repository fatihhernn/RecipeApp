package com.fatihhernn.recipes.ui.fragments.foodJokeOther

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.databinding.FragmentOtherBinding
import com.fatihhernn.recipes.util.Constants.Companion.API_KEY
import com.fatihhernn.recipes.util.NetworkResult
import com.fatihhernn.recipes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    private var foodJoke=""

    private val mainViewModel by viewModels<MainViewModel>()

    private var _binding:FragmentOtherBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding= FragmentOtherBinding.inflate(inflater,container,false)

        binding.lifecycleOwner=viewLifecycleOwner
        binding.mainViewModel=mainViewModel

        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner,{
            response ->
                when(response){
                    is NetworkResult.Success -> {
                        binding.foodJokeTextView.text=response.data?.text
                        if(response.data!=null){
                            foodJoke=response.data.text
                        }
                    }
                    is NetworkResult.Error ->{
                        loadDataFromCache()
                        Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is NetworkResult.Loading->{
                        Log.d("FoodJokeFragment","Loading")
                    }
                }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.share_food_joke_menu){
            val shareIntent=Intent().apply {
                this.action=Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT,foodJoke)
                this.type="text/plain"
            }
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner,{ database->
                if (database.isNotEmpty()&&database!=null){
                    binding.foodJokeTextView.text=database[0].foodJoke.text
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}