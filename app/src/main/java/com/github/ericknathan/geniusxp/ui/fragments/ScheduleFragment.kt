package com.github.ericknathan.geniusxp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.enums.HttpStatusCode
import com.github.ericknathan.geniusxp.models.Event
import com.github.ericknathan.geniusxp.models.EventDay
import com.github.ericknathan.geniusxp.models.Lecture
import com.github.ericknathan.geniusxp.services.api.ApiClient
import com.github.ericknathan.geniusxp.ui.recyclerview.adapter.LectureListAdapter
import com.github.ericknathan.geniusxp.utils.Constants
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ScheduleFragment : Fragment() {
    private var eventDays = listOf<EventDay>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        val bundle = activity?.intent?.extras
        val event = Gson().fromJson(bundle?.getString("event"), Event::class.java)

        loadEventDays(view, event.id)

        return view
    }

    private fun loadEventDays(view: View, eventId: Long) {
        val client = ApiClient.getClient(view.context)
        val gson = Gson()

        val request = Request.Builder()
            .url("${Constants.API_URL}/events/$eventId/days")
            .get()
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "Erro ao carregar os dias de evento", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val responseBody = gson.fromJson(response.body?.string(), List::class.java)
                println("RESPONSE: " + responseBody)

                activity?.runOnUiThread {
                    when (response.code) {
                        HttpStatusCode.OK -> {
                            eventDays = responseBody.map { lecture ->
                                gson.fromJson(gson.toJson(lecture), EventDay::class.java)
                            }.sortedBy { it.startDate }

                            val selectedDate = view.findViewById<TextView>(R.id.eventDay)
                            val formatter = DateTimeFormatter.ofPattern("dd/MM")
                            selectedDate.text = formatter.format(LocalDateTime.parse(eventDays[0].startDate))

                            loadLectures(view, eventDays[0].id)
                        }

                        else -> {
                            Toast.makeText(activity, "Erro ao carregar os dias de evento", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        client.newCall(request).enqueue(response)
    }

    private fun loadLectures(view: View, dayId: Long) {
        val client = ApiClient.getClient(view.context)
        val gson = Gson()

        val request = Request.Builder()
            .url("${Constants.API_URL}/event-days/$dayId/lectures")
            .get()
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "Erro ao carregar as palestras", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val responseBody = gson.fromJson(response.body?.string(), List::class.java)

                activity?.runOnUiThread {
                    when (response.code) {
                        HttpStatusCode.OK -> {
                            val speakers = responseBody.map { lecture ->
                                gson.fromJson(gson.toJson(lecture), Lecture::class.java)
                            }

                            val recyclerView = view.findViewById<RecyclerView>(R.id.lectureList)
                            recyclerView.adapter = LectureListAdapter(view.context, speakers)
                            recyclerView.layoutManager = LinearLayoutManager(view.context)
                        }

                        else -> {
                            Toast.makeText(activity, "Erro ao carregar as palestras", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        client.newCall(request).enqueue(response)
    }
}