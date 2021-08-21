package com.fatihhernn.recipes.ui.fragments.settings

import android.app.ActionBar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.databinding.FragmentOtherBinding
import com.fatihhernn.recipes.databinding.FragmentSettingBinding
import com.fatihhernn.recipes.models.User
import com.fatihhernn.recipes.models.profile.UserRequest
import com.fatihhernn.recipes.ui.activities.MainActivity
import com.fatihhernn.recipes.ui.fragments.foodJokeOther.FoodJokeFragment
import com.fatihhernn.recipes.util.Resource
import com.fatihhernn.recipes.util.gone
import com.fatihhernn.recipes.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private lateinit var _binding: FragmentSettingBinding

    private var image: Int = R.drawable.error_placeholder

    private val viewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        addListeners()
    }

    private fun setField(user: User?) {
        _binding.nameEditText.setText(user?.name)
        _binding.mailEditText.setText(user?.email)
        _binding.addressEditText.setText(user?.address)
        _binding.phoneNumberEditText.setText(user?.phone)

        image = FoodJokeFragment.getImageResource(user?.profileImage)
        _binding.avatarImageView.setImageResource(FoodJokeFragment.getImageResource(user?.profileImage))
    }

    private fun addListeners() {
        _binding.updateProfileButton.setOnClickListener { updateUser() }
    }
    private fun updateUser() {
        val name = _binding.nameEditText.text.toString()
        val mail = _binding.mailEditText.text.toString()
        val phone = _binding.phoneNumberEditText.text.toString()
        val address = _binding.addressEditText.text.toString()

        val user = UserRequest(mail, name, address, phone, image.toString(),1)
        viewModel.updateUser(user).observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    _binding.progressBar5.show()
                }
                Resource.Status.SUCCESS -> {
                    findNavController().navigate(R.id.action_settingFragment_to_foodJokeFragment)
                    _binding.progressBar5.gone()
                }
                Resource.Status.ERROR -> {
                    _binding.progressBar5.gone()
                }
            }
        })
    }


    private fun initViews() {
        viewModel.getUser().observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    _binding.progressBar5.show()
                }
                Resource.Status.SUCCESS -> {
                    setField(response.data?.user)
                    _binding.progressBar5.gone()
                }
                Resource.Status.ERROR -> {
                    _binding.progressBar5.gone()
                }
            }
        })
    }

}