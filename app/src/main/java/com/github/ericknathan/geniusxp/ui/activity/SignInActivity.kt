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
import com.github.ericknathan.geniusxp.models.forms.SignInForm
import com.github.ericknathan.geniusxp.services.api.ApiClient
import com.github.ericknathan.geniusxp.utils.Constants
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class SignInActivity : Activity() {
    private val client = ApiClient.getClient()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        val bundle = intent.extras;

        val buttonBack = findViewById<TextView>(R.id.backButton)
        val buttonForgotPassword = findViewById<TextView>(R.id.forgotPasswordButton)
        val buttonSignIn = findViewById<MaterialButton>(R.id.submitButton)
        val buttonCreateAccount = findViewById<MaterialButton>(R.id.createAccountButton)

        val emailInput = findViewById<TextView>(R.id.emailInput)
        val passwordInput = findViewById<TextView>(R.id.passwordInput)

        if(bundle?.getString("email") != null){
            val email = bundle.getString("email")
            emailInput.text = email
            passwordInput.requestFocus()
        }

        buttonBack.setOnClickListener { this.finish() }

        buttonForgotPassword.setOnClickListener {
            val intent = Intent(this, RecoverPasswordActivity::class.java)
            startActivity(intent)
        }

        buttonSignIn.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            submitSignIn(SignInForm(email, password))
        }

        buttonCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            this.finish()
        }
    }

    private fun submitSignIn(data: SignInForm) {
        val body = gson.toJson(data)
        val request = Request.Builder()
            .url("${Constants.API_URL}/auth/login")
            .post(body.toRequestBody("application/json".toMediaType()))
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@SignInActivity, "Erro ao realizar login.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val responseBody = gson.fromJson(response.body?.string(), Map::class.java)

                runOnUiThread {
                    val context = this@SignInActivity

                    when (response.code) {
                        HttpStatusCode.OK -> {
                            val accessToken = responseBody["token"].toString()

                            val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("accessToken", accessToken)
                            editor.apply()

                            val intent = Intent(context, HomeActivity::class.java)
                            startActivity(intent)
                            context.finish()
                        }

                        HttpStatusCode.UNAUTHORIZED -> {
                            Toast.makeText(context, "Credenciais incorretas.", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Toast.makeText(context, "Erro ao realizar login.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        client.newCall(request).enqueue(response)
    }
}