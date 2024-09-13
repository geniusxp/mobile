package com.github.ericknathan.geniusxp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.databinding.ActivityEventBinding
import com.github.ericknathan.geniusxp.ui.fragments.InteractionFragment
import com.github.ericknathan.geniusxp.ui.fragments.MapFragment
import com.github.ericknathan.geniusxp.ui.fragments.ScheduleFragment
import com.github.ericknathan.geniusxp.ui.fragments.SpeakersFragment

class EventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventBinding

    private val scheduleFragment = ScheduleFragment()
    private val speakersFragment = SpeakersFragment()
    private val interactionFragment = InteractionFragment()
    private val mapFragment = MapFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentFragment(scheduleFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.schedule -> setCurrentFragment(scheduleFragment)
                R.id.speakers -> setCurrentFragment(speakersFragment)
                R.id.interaction -> setCurrentFragment(interactionFragment)
                R.id.map -> setCurrentFragment(mapFragment)
                else -> {}
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentManager.fragments.forEach { fragmentTransaction.hide(it) }

        if (fragment.isAdded) {
            fragmentTransaction.show(fragment)
        } else {
            fragmentTransaction.add(R.id.frameLayout, fragment)
        }

        fragmentTransaction.commit()
    }
}