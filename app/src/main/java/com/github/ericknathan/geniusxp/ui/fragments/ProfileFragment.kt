package com.github.ericknathan.geniusxp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.services.getUserProfile
import com.github.ericknathan.geniusxp.ui.activity.SignInActivity
import com.github.ericknathan.geniusxp.ui.activity.WelcomeActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment() {
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
}