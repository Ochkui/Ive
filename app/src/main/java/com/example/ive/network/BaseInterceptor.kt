package com.example.ive.network

import com.example.ive.BuildConfig
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class BaseInterceptor:Interceptor,Authenticator {
    private val baseHeaders = mapOf(
        AUTHORIZATION to "$CLIENT_ID ${BuildConfig.ACCESS_KEY}",
        ACCEPT_VERSION to "v1"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        request.newBuilder()
            .addHeaders(baseHeaders)
            .build()
        return chain.proceed(request)
    }

    private fun Request.Builder.addHeaders(headers:Map<String,String>):Request.Builder{
        headers.forEach(this::addHeader)
        return this
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        TODO("Not yet implemented")
    }

    companion object{
        const val AUTHORIZATION = "Authorization"
        const val ACCEPT_VERSION = "Accept-Version"
        const val CLIENT_ID = "Client-ID"
    }
}