package com.fatihhernn.recipes.ui.fragments.foodJokeOther

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.databinding.FragmentOtherBinding
import com.fatihhernn.recipes.models.User
import com.fatihhernn.recipes.ui.activities.AuthActivity
import com.fatihhernn.recipes.util.Constants.Companion.API_KEY
import com.fatihhernn.recipes.util.NetworkResult
import com.fatihhernn.recipes.util.Resource
import com.fatihhernn.recipes.util.gone
import com.fatihhernn.recipes.util.show
import com.fatihhernn.recipes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    private var foodJoke=""

    private val mainViewModel by viewModels<MainViewModel>()
    private val otherViewModel by viewModels<OtherViewModel>()

    private var _binding:FragmentOtherBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding= FragmentOtherBinding.inflate(inflater,container,false)

        addListeners()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility=View.VISIBLE
        getProfile()
    }
    private fun getProfile() {
        otherViewModel.getUser().observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    setField(response.data?.user)
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.gone()
                    Toast.makeText(context, "Operation Failed", Toast.LENGTH_LONG).show()
                }
            }
        })
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
    private fun addListeners() {
        binding.logoutCardView.setOnClickListener {
            mainViewModel.logOut()
            val intent = Intent(context, AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
    private fun setField(user: User?) {
        binding.nameTextView.text = user?.name
        binding.mailTextView.text = user?.email
        binding.phoneNumberTextView.text = user?.phone
        binding.addressTextView.text = user?.address
        binding.profilePhotoImageView.setImageResource(getImageResource(user?.profileImage))
    }
    companion object {
        fun getImageResource(image : String?) : Int {
            val resource = try {
                image?.toInt()
            } catch (e : Exception) {
                Log.v("Profile Avatar", e.message.toString())
                R.drawable.placeholder_image
            }
            return resource ?: R.drawable.placeholder_image
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}