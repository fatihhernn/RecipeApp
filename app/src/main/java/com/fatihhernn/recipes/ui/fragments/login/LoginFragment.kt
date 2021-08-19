package com.fatihhernn.recipes.ui.fragments.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.databinding.FragmentLoginBinding
import com.fatihhernn.recipes.ui.activities.MainActivity
import com.fatihhernn.recipes.util.Resource
import com.fatihhernn.recipes.util.gone
import com.fatihhernn.recipes.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val viewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentLoginBinding.inflate(inflater,container,false)
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.loginBtn.setOnClickListener {
            val mail=_binding.emailText.text.toString()
            val password=_binding.passwordTextView.text.toString()

                if(mail.isNullOrEmpty()){
                    Toast.makeText(context,"Mail is required", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                if(password.isNullOrEmpty()){
                    Toast.makeText(context,"Password is required",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

            viewModel.login(mail,password).observe(viewLifecycleOwner, Observer {
                when(it.status){
                    Resource.Status.LOADING ->{
                        _binding.progressBar2.show()
                    }
                    Resource.Status.SUCCESS ->{
                        _binding.progressBar2.gone()
                        Log.d("Login Fragment","Giriş başarılı")
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }
                    Resource.Status.ERROR ->{
                        _binding.progressBar2.gone()
                        val dialog= AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage("${it.message}")
                            .setPositiveButton("ok"){dialog,button->
                                dialog.dismiss()
                            }
                        dialog.show()
                    }
                }
            })
        }
        _binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}