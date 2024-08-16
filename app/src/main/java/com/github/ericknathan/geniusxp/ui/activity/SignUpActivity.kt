package com.github.ericknathan.geniusxp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.github.ericknathan.geniusxp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val buttonBack = findViewById<TextView>(R.id.backButton)
        val inputBirthDate = findViewById<EditText>(R.id.dateInput)
        val buttonSignUp = findViewById<MaterialButton>(R.id.submitButton)
        val buttonAlreadyLogin = findViewById<MaterialButton>(R.id.alreadyLoginButton)

        buttonBack.setOnClickListener { this.finish() }

        inputBirthDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val constraints =CalendarConstraints.Builder()
                .setEnd(calendar.timeInMillis)
                .build()

            val datePicker = MaterialDatePicker.Builder.datePicker().setCalendarConstraints(constraints).build()
            datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener { milissecondsDate ->
                val date = Instant.ofEpochMilli(milissecondsDate)
                    .atZone(ZoneId.of("America/Sao_Paulo"))
                    .withZoneSameInstant(ZoneId.ofOffset("UTC", ZoneOffset.UTC))
                    .toLocalDate()

                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt-br"))
                inputBirthDate.setText(formatter.format(date));
            }
        }

        buttonSignUp.setOnClickListener {
            Log.i("TODO", "do signup method")
        }
        buttonAlreadyLogin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            this.finish()
        }
    }
}