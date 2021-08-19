package com.fatihhernn.recipes.ui.fragments.register

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.databinding.FragmentRegisterBinding
import com.fatihhernn.recipes.util.Resource
import com.fatihhernn.recipes.util.gone
import com.fatihhernn.recipes.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {


    private lateinit var _binding: FragmentRegisterBinding
    private val viewModel:RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentRegisterBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.registerBtn.setOnClickListener {
            var userName= _binding.userNameText.text.toString()
            var email=_binding.emailTextView.text.toString()
            var password=_binding.passwordTextView.text.toString()

            if(userName.isNullOrEmpty()){
                Toast.makeText(context,"Username is required",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(email.isNullOrEmpty()){
                Toast.makeText(context,"Mail is required",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(password.isNullOrEmpty()){
                Toast.makeText(context,"Password is required",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            viewModel.register(email,userName,password).observe(
                viewLifecycleOwner, Observer {
                    when(it.status){
                        Resource.Status.LOADING ->{
                            _binding.progressBar3.show()
                        }
                        Resource.Status.SUCCESS ->{
                            Toast.makeText(context,"Register is success! ${it.message}", Toast.LENGTH_LONG).show()
                            _binding.progressBar3.gone()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                        Resource.Status.ERROR ->{
                            val dialog = AlertDialog.Builder(context)
                                .setTitle("Error")
                                .setMessage("${it.message}")
                                .setPositiveButton("ok") { dialog, button ->
                                    dialog.dismiss()
                                }
                            dialog.show()
                            _binding.progressBar3.gone()
                        }
                    }
                }
            )
        }
    }



}