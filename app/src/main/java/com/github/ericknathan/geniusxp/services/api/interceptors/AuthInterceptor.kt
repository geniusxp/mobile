package com.github.ericknathan.geniusxp.services.api.interceptors

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.github.ericknathan.geniusxp.enums.HttpStatusCode
import com.github.ericknathan.geniusxp.ui.activity.SignInActivity
import com.github.ericknathan.geniusxp.ui.activity.WelcomeActivity
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


internal class AuthInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)

        if(response.code == HttpStatusCode.UNAUTHORIZED) {
            val intent = Intent(context, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)

            Toast.makeText(context, "Sessão expirada. Faça login novamente.", Toast.LENGTH_SHORT).show()
        }

        return response
    }
}