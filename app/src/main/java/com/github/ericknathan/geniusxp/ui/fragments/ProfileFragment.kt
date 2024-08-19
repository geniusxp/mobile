package com.github.ericknathan.geniusxp.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.ericknathan.geniusxp.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val avatarView = view.findViewById<ImageView>(R.id.userAvatar)
        Picasso.get().load("https://github.com/ericknathan.png").into(avatarView);

        val usernameText = view.findViewById<TextView>(R.id.userName)
        usernameText.text = "Erick Nathan"

        val aboutText = view.findViewById<TextView>(R.id.userAbout)
        aboutText.text = "Entusiasta de tecnologia com uma paixão por inovação e eventos tecnológicos. Adoro explorar as últimas tendências e descobrir como elas podem ser aplicadas para transformar a maneira como vivemos e trabalhamos."

        val interestsView = view.findViewById<ChipGroup>(R.id.userInterests)
        val interestsList = listOf("Tecnologia", "Jogos Online", "IoT e Robótica", "Filmes", "Música", "Outros")

        for (interest in interestsList) {
            val chip = layoutInflater.inflate(R.layout.interest_item, interestsView, false) as Chip
            chip.text = interest
            interestsView.addView(chip)
        }
        
        return view
    }
}