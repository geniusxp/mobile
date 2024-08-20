package com.github.ericknathan.geniusxp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.github.ericknathan.geniusxp.R
import com.google.android.material.button.MaterialButton

class SignInActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val buttonBack = findViewById<TextView>(R.id.backButton)
        val buttonForgotPassword = findViewById<TextView>(R.id.forgotPasswordButton)
        val buttonSignIn = findViewById<MaterialButton>(R.id.submitButton)
        val buttonCreateAccount = findViewById<MaterialButton>(R.id.createAccountButton)

        buttonBack.setOnClickListener { this.finish() }

        buttonForgotPassword.setOnClickListener {
            val intent = Intent(this, RecoverPasswordActivity::class.java)
            startActivity(intent)
        }

        buttonSignIn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        buttonCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            this.finish()
        }
    }
}