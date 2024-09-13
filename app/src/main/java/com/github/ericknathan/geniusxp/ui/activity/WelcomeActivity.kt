package com.github.ericknathan.geniusxp.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.github.ericknathan.geniusxp.R
import com.google.android.material.button.MaterialButton

class WelcomeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("accessToken", null)

        if (accessToken != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            return;
        }

        setContentView(R.layout.activity_welcome)

        val buttonLogin = findViewById<MaterialButton>(R.id.loginButton)
        val buttonCreateAccount = findViewById<MaterialButton>(R.id.createAccountButton)

        buttonLogin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        buttonCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}