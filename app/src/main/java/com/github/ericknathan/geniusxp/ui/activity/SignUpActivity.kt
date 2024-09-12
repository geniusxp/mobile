package com.github.ericknathan.geniusxp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.enums.HttpStatusCode
import com.github.ericknathan.geniusxp.models.forms.SignInForm
import com.github.ericknathan.geniusxp.models.forms.SignUpForm
import com.github.ericknathan.geniusxp.utils.Constants
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class SignUpActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    private val gson = Gson()

    private var birthDate = "";
    private var isDatePickerOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val buttonBack = findViewById<TextView>(R.id.backButton)
        val inputBirthDate = findViewById<EditText>(R.id.dateInput)
        val buttonSignUp = findViewById<MaterialButton>(R.id.submitButton)
        val buttonAlreadyLogin = findViewById<MaterialButton>(R.id.alreadyLoginButton)

        buttonBack.setOnClickListener { this.finish() }

        inputBirthDate.setOnClickListener { openDatePicker() }

        buttonSignUp.setOnClickListener {
            val name = findViewById<EditText>(R.id.nameInput).text.toString()
            val email = findViewById<EditText>(R.id.emailInput).text.toString()
            val password = findViewById<EditText>(R.id.passwordInput).text.toString()
            val cpf = findViewById<EditText>(R.id.cpfInput).text.toString()

            submitSignUp(SignUpForm(name, email, password, cpf, birthDate))
        }

        buttonAlreadyLogin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            this.finish()
        }
    }

    private fun openDatePicker() {
        if(isDatePickerOpen) return

        isDatePickerOpen = true

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

            birthDate = date.toString()
            println(birthDate)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt-br"))
            findViewById<EditText>(R.id.dateInput).setText(formatter.format(date))
            findViewById<EditText>(R.id.cpfInput).requestFocus()
        }

        datePicker.addOnNegativeButtonClickListener {
            findViewById<EditText>(R.id.cpfInput).requestFocus()
            isDatePickerOpen = false
        }
    }

    private fun submitSignUp(data: SignUpForm) {
        val body = gson.toJson(data)
        val request = Request.Builder()
            .url("${Constants.API_URL}/auth/register")
            .post(body.toRequestBody("application/json".toMediaType()))
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@SignUpActivity, "Erro ao realizar cadastro.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                println(response.body?.string())

                runOnUiThread {
                    val context = this@SignUpActivity

                    when (response.code) {
                        HttpStatusCode.CREATED -> {
                            val intent = Intent(context, SignInActivity::class.java)

                            val bundle = Bundle()
                            bundle.putString("email", data.email)
                            intent.putExtras(bundle)

                            startActivity(intent)
                            Toast.makeText(context, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                            context.finish()
                        }

                        HttpStatusCode.CONFLICT -> {
                            Toast.makeText(context, "Usuário já cadastrado.", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Toast.makeText(context, "Erro ao realizar cadastro.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        client.newCall(request).enqueue(response)
    }
}