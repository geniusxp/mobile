package com.github.ericknathan.geniusxp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.github.ericknathan.geniusxp.R
import com.google.android.material.button.MaterialButton

class SignUpActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val buttonBack = findViewById<TextView>(R.id.backButton)
        val buttonSignUp = findViewById<MaterialButton>(R.id.submitButton)
        val buttonAlreadyLogin = findViewById<MaterialButton>(R.id.alreadyLoginButton)

        buttonBack.setOnClickListener { this.finish() }

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