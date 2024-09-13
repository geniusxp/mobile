package com.github.ericknathan.geniusxp.ui.activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.enums.HttpStatusCode
import com.github.ericknathan.geniusxp.models.forms.RecoverPasswordForm
import com.github.ericknathan.geniusxp.models.forms.SignInForm
import com.github.ericknathan.geniusxp.services.api.ApiClient
import com.github.ericknathan.geniusxp.utils.Constants
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class RecoverPasswordActivity : Activity() {
    private val client = ApiClient.getClient()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        val emailInput = findViewById<TextView>(R.id.emailInput)
        val buttonBack = findViewById<TextView>(R.id.backButton)
        val buttonRecoverPassword = findViewById<MaterialButton>(R.id.submitButton)

        buttonBack.setOnClickListener { this.finish() }

        buttonRecoverPassword.setOnClickListener {
            val email = emailInput.text.toString()

            submitRecoverPassword(RecoverPasswordForm(email))
        }
    }

    private fun submitRecoverPassword(data: RecoverPasswordForm) {
        val body = gson.toJson(data)
        val request = Request.Builder()
            .url("${Constants.API_URL}/auth/recover-password")
            .post(body.toRequestBody("application/json".toMediaType()))
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@RecoverPasswordActivity, "Erro ao enviar solicitação de recuperação de senha.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                runOnUiThread {
                    val context = this@RecoverPasswordActivity

                    when (response.code) {
                        HttpStatusCode.OK -> {
                            Toast.makeText(context, "Solicitação de recuperação de senha enviada com sucesso.", Toast.LENGTH_SHORT).show()
                            context.finish()
                        }

                        HttpStatusCode.NOT_FOUND -> {
                            Toast.makeText(context, "Cadastro não encontrado.", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Toast.makeText(context, "Erro ao enviar solicitação de recuperação de senha.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        client.newCall(request).enqueue(response)
    }
}