package com.github.ericknathan.geniusxp.ui.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.models.Ticket
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.EnumMap
import java.util.UUID


class TicketDetailsActivity : AppCompatActivity() {
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_details)

        val bundle = intent.extras;
        val ticket: Ticket = gson.fromJson(bundle?.getString("ticket"), Ticket::class.java)

        loadHeader(ticket)

        val ticketBanner = findViewById<ImageView>(R.id.ticketBanner)
        Picasso.get().load(ticket.event.imageUrl).into(ticketBanner)

        val ticketType = findViewById<TextView>(R.id.ticketType)
        ticketType.text = ticket.ticketType.category

        val date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM yyyy")

        val eventDate = findViewById<TextView>(R.id.ticketDate)
        eventDate.text = date.format(formatter).capitalize()

        val qrCode = findViewById<ImageView>(R.id.ticketBarCode)
        qrCode.setImageBitmap(generateQRCodeBitmap(ticket.ticketNumber))

        val expirationBar = findViewById<ProgressBar>(R.id.expirationBar)
        val animation = ObjectAnimator.ofInt(expirationBar, "progress", 59, 0).apply {
            duration = 1000 * 60 // 1 minute
            start()
        }

        animation.addUpdateListener {
            if (it.animatedValue == 0) {
                animation.start()
                qrCode.setImageBitmap(generateQRCodeBitmap(UUID.randomUUID().toString()))
            }
        }

        val accessEventButton = findViewById<Button>(R.id.accessEventButton)
        accessEventButton.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)

            val newBundle = Bundle()
            newBundle.putString("event", gson.toJson(ticket.event))

            intent.putExtras(newBundle)
            startActivity(intent)
        }

    }

    @Throws(WriterException::class)
    fun generateQRCodeBitmap(str: String?): Bitmap {
        val hints: MutableMap<EncodeHintType, Any> = EnumMap(
            EncodeHintType::class.java
        )
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
        hints[EncodeHintType.MARGIN] = 0

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 400, 400, hints)

        val w = bitMatrix.width
        val h = bitMatrix.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            for (x in 0 until w) {
                // get @color/zinc_500
                pixels[y * w + x] = if (bitMatrix[x, y]) ContextCompat.getColor(this, R.color.zinc_950) else Color.TRANSPARENT
            }
        }

        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
        return bitmap
    }

    fun loadHeader(ticket: Ticket) {
        val eventImage = findViewById<ImageView>(R.id.eventImage)
        Picasso.get().load(ticket.event.imageUrl).into(eventImage)

        val eventName = findViewById<TextView>(R.id.eventName)
        eventName.text = ticket.event.name
    }
}