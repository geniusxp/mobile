package com.github.ericknathan.geniusxp.ui.fragments

import android.app.Activity.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.enums.HttpStatusCode
import com.github.ericknathan.geniusxp.models.Speaker
import com.github.ericknathan.geniusxp.models.forms.LogoutData
import com.github.ericknathan.geniusxp.models.forms.SignInForm
import com.github.ericknathan.geniusxp.services.api.ApiClient
import com.github.ericknathan.geniusxp.services.getUserProfile
import com.github.ericknathan.geniusxp.ui.activity.HomeActivity
import com.github.ericknathan.geniusxp.ui.activity.SignInActivity
import com.github.ericknathan.geniusxp.ui.activity.WelcomeActivity
import com.github.ericknathan.geniusxp.ui.recyclerview.adapter.SpeakerListAdapter
import com.github.ericknathan.geniusxp.utils.Constants
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ProfileFragment : Fragment() {
    private val client = ApiClient.getClient()
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val logoutButton = view.findViewById<TextView>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            logout()
        }

        requireActivity().runOnUiThread {
            setUserData(view)
        }

        return view
    }

    private fun setUserData(view: View) {
        lifecycleScope.launch {
            val user = getUserProfile(requireActivity()) ?: return@launch

            val usernameText = view.findViewById<TextView>(R.id.userName)
            usernameText.text = user.name

            val avatarView = view.findViewById<ImageView>(R.id.userAvatar)
            Picasso.get()
                .load(user.avatarUrl)
                .into(avatarView);

            val aboutText = view.findViewById<TextView>(R.id.userAbout)
            aboutText.text = user.description ?: "Sem descrição"

            val interestsView = view.findViewById<ChipGroup>(R.id.userInterests)
            val interestsList = user.interests?.split(",") ?: emptyList()

            for (interest in interestsList) {
                val chip = layoutInflater.inflate(
                    R.layout.comp_interest_item,
                    interestsView,
                    false
                ) as Chip
                chip.text = interest
                interestsView.addView(chip)
            }
        }
    }

    private fun logout() {
        val body = gson.toJson(LogoutData(activity?.getSharedPreferences("user", MODE_PRIVATE)?.getString("accessToken", "") ?: ""))
        val request = Request.Builder()
            .url("${Constants.API_URL}/auth/logout")
            .delete(body.toRequestBody("application/json".toMediaType()))
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "Não foi possível deslogar o usuário.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                activity?.runOnUiThread {
                    when (response.code) {
                        HttpStatusCode.OK -> {
                            val sharedPreferences = requireActivity().getSharedPreferences("user", 0)
                            sharedPreferences.edit().clear().apply()

                            activity?.runOnUiThread {
                                val intent = Intent(activity, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                activity?.startActivity(intent)
                                activity?.finish()

                                Toast.makeText(activity, "Logout realizado com sucesso.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        else -> {
                            Toast.makeText(context, "Não foi possível deslogar o usuário.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        client.newCall(request).enqueue(response)
    }
}