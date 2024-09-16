package com.github.ericknathan.geniusxp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.enums.HttpStatusCode
import com.github.ericknathan.geniusxp.models.Ticket
import com.github.ericknathan.geniusxp.services.api.ApiClient
import com.github.ericknathan.geniusxp.ui.recyclerview.adapter.TicketListAdapter
import com.github.ericknathan.geniusxp.utils.Constants
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import java.io.IOException

class TicketsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_tickets, container, false)

        loadMyTickets(view)

        return view
    }

    private fun loadMyTickets(view: View) {
        val client = ApiClient.getClient(view.context)
        val gson = Gson()

        val request = Request.Builder()
            .url("${Constants.API_URL}/tickets/me")
            .get()
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "Erro ao carregar os ingressos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val responseBody = gson.fromJson(response.body?.string(), List::class.java)

                activity?.runOnUiThread {
                    when (response.code) {
                        HttpStatusCode.OK -> {
                            val tickets = responseBody.map { ticket ->
                                gson.fromJson(gson.toJson(ticket), Ticket::class.java)
                            }

                            val recyclerView = view.findViewById<RecyclerView>(R.id.ticketList)
                            recyclerView.adapter = TicketListAdapter(view.context, tickets)
                            recyclerView.layoutManager = LinearLayoutManager(view.context)
                        }

                        else -> {
                            Toast.makeText(activity, "Erro ao carregar os ingressos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        client.newCall(request).enqueue(response)
    }
}