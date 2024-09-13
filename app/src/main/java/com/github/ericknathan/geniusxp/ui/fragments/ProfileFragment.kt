package com.github.ericknathan.geniusxp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.services.getUserProfile
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

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
}