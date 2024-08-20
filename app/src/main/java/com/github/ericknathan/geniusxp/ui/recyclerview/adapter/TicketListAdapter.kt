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
import com.github.ericknathan.geniusxp.models.Ticket
import com.github.ericknathan.geniusxp.ui.activity.TicketDetailsActivity
import com.squareup.picasso.Picasso

class TicketListAdapter(
    private val context: Context,
    private val tickets: List<Ticket>
) : RecyclerView.Adapter<TicketListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun sync(ticket: Ticket) {
            val ticketImage = itemView.findViewById<ImageView>(R.id.ticketImage)
            Picasso.get().load(ticket.avatarURL).into(ticketImage);

            val ticketTitle = itemView.findViewById<TextView>(R.id.ticketTitle)
            ticketTitle.text = ticket.title

            val ticketAddress = itemView.findViewById<TextView>(R.id.ticketAddress)
            ticketAddress.text = ticket.address

            val ticketAvailable = itemView.findViewById<TextView>(R.id.ticketAvailable)
            ticketAvailable.text = "${ticket.available} ${if (ticket.available > 1) "disponíveis" else "disponível"}"

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, TicketDetailsActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.comp_card_ticket, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tickets.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.sync(ticket)
    }

}
