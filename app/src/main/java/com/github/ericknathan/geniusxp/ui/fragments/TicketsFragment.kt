package com.github.ericknathan.geniusxp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.models.Ticket
import com.github.ericknathan.geniusxp.ui.recyclerview.adapter.TicketListAdapter

class TicketsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tickets, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.ticketList)
        recyclerView.adapter = TicketListAdapter(view.context, listOf(
            Ticket("FIAP Next 2024", "ARCA - Av. Manuel Bandeira, 360 - Vila Leopoldina", 3, "https://play-lh.googleusercontent.com/S70rI7VrwLic7_p-ax7iAOOopQhcPCzmqyLe5RLJmApTpkgTRaCwWsTNN1Uv1t_t3Pp5"),
            Ticket("VCT Americas 2024", "Ginásio Ibirapuera - Rua Manuel da Nobrega, 1361 - Ibirapuera", 1, "https://cdn.ome.lt/zYralL-pdy-XdmgMXkx_GmuskcM=/1200x630/smart/extras/conteudos/jogos-vct-americas-kickoff-2024.png")
        ))
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        return view
    }

}