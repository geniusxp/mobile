package com.github.ericknathan.geniusxp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.databinding.ActivityEventBinding
import com.github.ericknathan.geniusxp.ui.fragments.InteractionFragment
import com.github.ericknathan.geniusxp.ui.fragments.MapFragment
import com.github.ericknathan.geniusxp.ui.fragments.ProfileFragment
import com.github.ericknathan.geniusxp.ui.fragments.ScheduleFragment
import com.github.ericknathan.geniusxp.ui.fragments.SpeakersFragment
import com.github.ericknathan.geniusxp.ui.fragments.TicketsFragment

class EventActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(ScheduleFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.schedule -> replaceFragment(ScheduleFragment())
                R.id.speakers -> replaceFragment(SpeakersFragment())
                R.id.interaction -> replaceFragment(InteractionFragment())
                R.id.map -> replaceFragment(MapFragment())
                else -> {}
            }

            true
        }

    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}