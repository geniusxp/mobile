package com.github.ericknathan.geniusxp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.github.ericknathan.geniusxp.R

class InteractionFragment : Fragment() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_interaction, container, false)
        val youtubeVideoId = "7Ggx_UsW17o"

        val youtubeWebView = view.findViewById<WebView>(R.id.youtubeWebView)
        youtubeWebView.settings.javaScriptEnabled = true
        youtubeWebView.settings.loadWithOverviewMode = true
        youtubeWebView.settings.useWideViewPort = true
        youtubeWebView.settings.builtInZoomControls = true
        youtubeWebView.settings.displayZoomControls = false
        youtubeWebView.settings.setSupportZoom(true)
        youtubeWebView.settings.domStorageEnabled = true
        youtubeWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        youtubeWebView.settings.mediaPlaybackRequiresUserGesture = false

        youtubeWebView.loadUrl("https://www.youtube.com/embed/$youtubeVideoId?autoplay=1&modestbranding=1&autohide=1&showinfo=0&controls=0")


        return view
    }

}