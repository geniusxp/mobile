package com.github.ericknathan.geniusxp.ui.recyclerview.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.models.Lecture
import com.github.ericknathan.geniusxp.models.Ticket
import com.github.ericknathan.geniusxp.ui.activity.TicketDetailsActivity
import com.squareup.picasso.Picasso

class LectureListAdapter(
    private val context: Context,
    private val lectures: List<Lecture>
) : RecyclerView.Adapter<LectureListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun sync(lecture: Lecture) {
            val speakerImage = itemView.findViewById<ImageView>(R.id.speakerImage)
            Picasso.get().load(lecture.speaker.avatarURL).into(speakerImage);

            val speakerName = itemView.findViewById<TextView>(R.id.speakerName)
            speakerName.text = lecture.speaker.name

            val lectureName = itemView.findViewById<TextView>(R.id.lectureName)
            lectureName.text = lecture.title

            val lectureInfo = itemView.findViewById<TextView>(R.id.lectureInfo)
            lectureInfo.text = "${lecture.hour} • ${lecture.place} • ${lecture.speaker.nationality}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.comp_card_lecture, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lectures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lecture = lectures[position]
        holder.sync(lecture)
    }

}
