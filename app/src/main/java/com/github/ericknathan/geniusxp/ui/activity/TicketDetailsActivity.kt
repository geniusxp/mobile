package com.github.ericknathan.geniusxp.ui.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.ericknathan.geniusxp.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.squareup.picasso.Picasso
import java.util.EnumMap
import java.util.UUID


class TicketDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_details)

        val ticketBanner = findViewById<ImageView>(R.id.ticketBanner)
        Picasso.get().load("https://i.ytimg.com/vi/7Ggx_UsW17o/maxresdefault.jpg").into(ticketBanner)

        val qrCode = findViewById<ImageView>(R.id.ticketBarCode)
        qrCode.setImageBitmap(generateQRCodeBitmap(UUID.randomUUID().toString()))

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
}