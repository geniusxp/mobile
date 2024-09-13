package com.github.ericknathan.geniusxp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.services.api.ApiClient
import com.github.ericknathan.geniusxp.services.getUserProfile
import com.github.ericknathan.geniusxp.utils.Constants
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


class InteractionFragment : Fragment() {
    private val client = ApiClient.getClient(this.requireContext())

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_interaction, container, false)

        val youtubeWebView = view.findViewById<WebView>(R.id.youtubeWebView)
        loadYoutubeVideo(youtubeWebView)

        val sendMessageButton = view.findViewById<View>(R.id.sendMessageButton)
        val messageInput = view.findViewById<EditText>(R.id.messageInput)
        val messageList = view.findViewById<View>(R.id.messageList)

        sendMessageButton.setOnClickListener {
            sendMessage(messageInput.text.toString(), messageList)
            messageInput.text.clear()
        }

        return view
    }

    private fun loadYoutubeVideo(youtubeWebView: WebView) {
        val youtubeVideoId = "7Ggx_UsW17o"

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
    }

    private fun sendMessage(message: String, messageListView: View) {
        lifecycleScope.launch {
            val user = getUserProfile(requireActivity()) ?: return@launch

                val messageView = layoutInflater.inflate(
                    R.layout.comp_message_item,
                    messageListView as ViewGroup,
                    false
                )
                Picasso.get().load(user.avatarUrl)
                    .into(messageView.findViewById<ImageView>(R.id.avatarImage));

                val spannable = SpannableString("${user.name}: $message")
                val usernameStyles = TextAppearanceSpan(requireContext(), R.style.MessageUsername)
                spannable.setSpan(usernameStyles, 0, user.name.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                messageView.findViewById<TextView>(R.id.messageText).text = spannable
                messageListView.addView(messageView)

                withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("${Constants.API_URL_CHAT}/event/1/send-message?message=$message")
                        .post("".toRequestBody("application/json".toMediaType()))
                        .build()

                    client.newCall(request).execute()
                }
        }
    }
}