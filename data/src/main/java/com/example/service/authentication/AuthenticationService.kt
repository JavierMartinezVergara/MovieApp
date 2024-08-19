package com.example.service.authentication

import com.example.model.request.RequestSession
import com.example.model.response.ResponseAuthentication
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationService {
    @GET("authentication/token/new")
    suspend fun createToken(): ResponseAuthentication

    @POST("authentication/token/validate_with_login")
    suspend fun createSession(
        @Body sessionParams: RequestSession,
    ): ResponseAuthentication
}
