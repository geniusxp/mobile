package com.github.ericknathan.geniusxp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.github.ericknathan.geniusxp.R
import com.google.android.material.button.MaterialButton

class RecoverPasswordActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        val buttonBack = findViewById<TextView>(R.id.backButton)

        buttonBack.setOnClickListener { this.finish() }
    }
}