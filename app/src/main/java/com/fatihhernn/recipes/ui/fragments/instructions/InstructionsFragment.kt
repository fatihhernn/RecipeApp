package com.fatihhernn.recipes.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.fatihhernn.recipes.R
import com.fatihhernn.recipes.models.Result
import com.fatihhernn.recipes.util.Constants

class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_instructions, container, false)

        val args = arguments
        val myBundle:Result? =args?.getParcelable(Constants.RECIPE_RESULT_KEY)
        
        view.findViewById<WebView>(R.id.instructions_webView).webViewClient = object : WebViewClient() {}
        val webSiteUrl:String=myBundle!!.sourceUrl
        view.findViewById<WebView>(R.id.instructions_webView).loadUrl(webSiteUrl)

        return view
    }
}