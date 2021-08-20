package layout.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fatihhernn.recipes.databinding.FragmentPrepareBinding
import com.fatihhernn.recipes.models.onBoarding.OnBoardingData

class PrepareFragment(private val data: OnBoardingData):Fragment() {
    private lateinit var binding: FragmentPrepareBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPrepareBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.imageView.setImageResource(data.image)
        binding.txtDescription.text = data.description
        binding.txtTitle.text = data.title
    }
}