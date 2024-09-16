package com.github.ericknathan.geniusxp.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.enums.Language
import com.github.ericknathan.geniusxp.models.Lecture
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LectureListAdapter(
    private val context: Context,
    private val lectures: List<Lecture>
) : RecyclerView.Adapter<LectureListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun sync(lecture: Lecture) {
            val speakerImage = itemView.findViewById<ImageView>(R.id.speakerImage)
            Picasso.get().load(lecture.speaker.avatarUrl).into(speakerImage);

            val speakerName = itemView.findViewById<TextView>(R.id.speakerName)
            speakerName.text = lecture.speaker.name

            val lectureName = itemView.findViewById<TextView>(R.id.lectureName)
            lectureName.text = lecture.name

            val startHour = LocalDateTime.parse(lecture.date)
            val formatter = DateTimeFormatter.ofPattern("HH:mm")

            val lectureInfo = itemView.findViewById<TextView>(R.id.lectureInfo)
            lectureInfo.text = "${startHour.format(formatter)} • ${lecture.description} • ${Language.valueOf(lecture.speaker.language).flag}"
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
