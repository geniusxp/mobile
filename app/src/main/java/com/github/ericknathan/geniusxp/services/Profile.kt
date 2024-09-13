package com.github.ericknathan.geniusxp.services

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.github.ericknathan.geniusxp.models.User
import com.github.ericknathan.geniusxp.ui.activity.SignInActivity
import com.github.ericknathan.geniusxp.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

suspend fun getUserProfile(activity: FragmentActivity): User? {
    val sharedPreferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE)
    return withContext(Dispatchers.IO) {
        try {
            val accessToken = sharedPreferences.getString("accessToken", null)
            val id = sharedPreferences.getInt("id", -1)

            if (id == -1) {
                val client = OkHttpClient()
                val gson = Gson()

                val request = Request.Builder()
                    .url("${Constants.API_URL}/users/me")
                    .header("Authorization", "Bearer $accessToken")
                    .get()
                    .build()

                val responseBody = client.newCall(request).execute().body?.string()
                val user = gson.fromJson(responseBody, User::class.java)
                    ?: throw IOException("Erro ao carregar dados do usuário")

                val editor = sharedPreferences.edit()
                editor.putInt("id", user.id)
                editor.putString("name", user.name)
                editor.putString("email", user.name)
                editor.putString("avatarUrl", user.avatarUrl ?: "https://api.dicebear.com/9.x/glass/png?seed=${user.id}")
                editor.putString("description", user.description ?: "Não há informações sobre o usuário")
                editor.putString("interests", user.interests)
                editor.apply()

                user
            } else {
                val userId = sharedPreferences.getInt("id", 0);

                User(
                    userId,
                    sharedPreferences.getString("name", null)!!,
                    sharedPreferences.getString("email", null)!!,
                    sharedPreferences.getString("avatarUrl", "https://api.dicebear.com/9.x/glass/png?seed=${userId}"),
                    sharedPreferences.getString("description", "Não há informações sobre o usuário"),
                    sharedPreferences.getString("interests", null)
                )
            }
        } catch (e: IOException) {
            sharedPreferences.edit().clear().apply()

            withContext(Dispatchers.Main) {
                activity.runOnUiThread {
                    val intent = Intent(activity, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    activity.startActivity(intent)
                    activity.finish()

                    Toast.makeText(activity, "Sessão expirada, faça login novamente", Toast.LENGTH_LONG).show()
                }
            }

            null
        }
    }
}

