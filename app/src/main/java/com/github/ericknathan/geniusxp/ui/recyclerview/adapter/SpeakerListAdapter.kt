package com.github.ericknathan.geniusxp.ui.recyclerview.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.models.Lecture
import com.github.ericknathan.geniusxp.models.Speaker
import com.github.ericknathan.geniusxp.models.Ticket
import com.github.ericknathan.geniusxp.ui.activity.TicketDetailsActivity
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class SpeakerListAdapter(
    private val context: Context,
    private val speakers: List<Speaker>
) : RecyclerView.Adapter<SpeakerListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun sync(speaker: Speaker) {
            val speakerImage = itemView.findViewById<ShapeableImageView>(R.id.speakerImage)
            Picasso.get().load(speaker.avatarURL).into(speakerImage);

            val speakerName = itemView.findViewById<TextView>(R.id.speakerName)
            speakerName.text = speaker.name

            val speakerNationality = itemView.findViewById<TextView>(R.id.speakerNationality)
            speakerNationality.text = speaker.nationality

            val speakerBio = itemView.findViewById<TextView>(R.id.speakerBio)
            speakerBio.text = speaker.bio

            val linkedinButton = itemView.findViewById<Button>(R.id.linkedinButton)
            linkedinButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(speaker.linkedInURL)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.comp_card_speaker, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = speakers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val speaker = speakers[position]
        holder.sync(speaker)
    }

}
