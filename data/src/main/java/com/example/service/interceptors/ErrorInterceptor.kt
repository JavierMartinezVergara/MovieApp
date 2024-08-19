package com.example.service.interceptors

import com.example.model.response.ErrorResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (!response.isSuccessful) {
            val errorBody = response.body?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            throw Exception("Error: ${errorResponse.message}")
        }
        return response
    }
}
