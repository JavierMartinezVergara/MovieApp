package com.example.data.service

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class AuthInterceptor(
    private val accessToken: String,
) : Interceptor {
    override fun intercept(chain: Chain): Response {
        val originalRequest = chain.request()

        val requestBuilder =
            originalRequest
                .newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer $accessToken")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
