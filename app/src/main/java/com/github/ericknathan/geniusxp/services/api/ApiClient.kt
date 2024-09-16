package com.github.ericknathan.geniusxp.services.api

import android.content.Context
import com.github.ericknathan.geniusxp.services.api.interceptors.AuthInterceptor
import okhttp3.OkHttpClient

object ApiClient {
    private var client: OkHttpClient? = null
    private var clientAuth: OkHttpClient? = null

    fun getClient(context: Context): OkHttpClient {
        if (clientAuth == null) {
            clientAuth = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .build()
        }
        return clientAuth!!
    }

    fun getClient(): OkHttpClient {
        if (client == null) {
            client = OkHttpClient.Builder().build()
        }
        return client!!
    }
}