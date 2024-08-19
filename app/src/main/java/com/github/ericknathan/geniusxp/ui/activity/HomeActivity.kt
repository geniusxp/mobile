package com.github.ericknathan.geniusxp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ericknathan.geniusxp.ui.fragments.ProfileFragment
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.ui.fragments.TicketsFragment
import com.github.ericknathan.geniusxp.databinding.ActivityHomeBinding
import com.github.ericknathan.geniusxp.models.Ticket
import com.github.ericknathan.geniusxp.ui.recyclerview.adapter.TicketListAdapter

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(TicketsFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.tickets -> replaceFragment(TicketsFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
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