package com.fatihhernn.recipes.ui.fragments.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.databinding.FragmentSplashBinding
import com.fatihhernn.recipes.ui.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    lateinit var lottieView: LottieAnimationView
    private val viewModel:SplashViewModel by viewModels()
    private lateinit var _binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSplashBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lottieView = view.findViewById(R.id.animation_view)
        lottieView.addAnimatorListener(object :Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
                Log.d("SplashFragment Animation","onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animator?) {
                viewModel.observeNavigationLiveData().observe(viewLifecycleOwner, Observer {
                    when(it){
                        "auth" -> findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                        "home" -> {
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    }
                })
                viewModel.checkTokenAndNavigation()
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.d("SplashFragment Animation","onAnimationCancel")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                Log.d("SplashFragment Animation","onAnimationRepeat")
            }

        })
    }
}