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
import com.github.ericknathan.geniusxp.models.Event
import com.github.ericknathan.geniusxp.models.Lecture
import com.github.ericknathan.geniusxp.models.Speaker
import com.github.ericknathan.geniusxp.models.Ticket
import com.github.ericknathan.geniusxp.services.api.ApiClient
import com.github.ericknathan.geniusxp.ui.recyclerview.adapter.LectureListAdapter
import com.github.ericknathan.geniusxp.ui.recyclerview.adapter.SpeakerListAdapter
import com.github.ericknathan.geniusxp.ui.recyclerview.adapter.TicketListAdapter
import com.github.ericknathan.geniusxp.utils.Constants
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import java.io.IOException

class SpeakersFragment : Fragment() {
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_speakers, container, false)

        val bundle = activity?.intent?.extras
        val event = gson.fromJson(bundle?.getString("event"), Event::class.java)

        loadSpeakers(view, event.id)

        return view
    }

    private fun loadSpeakers(view: View, eventId: Long) {
        val client = ApiClient.getClient(view.context)
        val gson = Gson()

        val request = Request.Builder()
            .url("${Constants.API_URL}/events/${eventId}/speakers")
            .get()
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "Erro ao carregar os palestrantes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val responseBody = gson.fromJson(response.body?.string(), List::class.java)

                activity?.runOnUiThread {
                    when (response.code) {
                        HttpStatusCode.OK -> {
                            val speakers = responseBody.map { speaker ->
                                gson.fromJson(gson.toJson(speaker), Speaker::class.java)
                            }

                            val recyclerView = view.findViewById<RecyclerView>(R.id.speakerList)
                            recyclerView.adapter = SpeakerListAdapter(view.context, speakers)
                            recyclerView.layoutManager = LinearLayoutManager(view.context)
                        }

                        else -> {
                            Toast.makeText(activity, "Erro ao carregar os palestrantes", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        client.newCall(request).enqueue(response)
    }
}