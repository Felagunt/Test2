package com.example.courses.data.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class LocalJsonInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val json = context.assets.open("courses.json").bufferedReader()
            .use { it.readText() }
        return Response.Builder()
            .code(200)
            .message(json)
            .protocol(Protocol.HTTP_1_1)
            .request(request)
            .body(json.toResponseBody("application/json".toMediaType()))
            .build()
    }
}
//
//class LocalJsonInterceptor(private val context: Context) : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        val urlPath = request.url.encodedPath
//
//        val fileName = when {
//            urlPath.endsWith("offers") -> "offers.json"
//            urlPath.endsWith("vacancies") -> "vacancies.json"
//            else -> null
//        }
//
//        return if (fileName != null) {
//            val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
//
//            Response.Builder()
//                .code(200)
//                .message(json)
//                .protocol(Protocol.HTTP_1_1)
//                .request(request)
//                .body(json.toResponseBody("application/json".toMediaType()))
//                .build()
//        } else {
//            Response.Builder()
//                .code(404)
//                .message("File not found")
//                .protocol(Protocol.HTTP_1_1)
//                .request(request)
//                .body("{}".toResponseBody("application/json".toMediaType()))
//                .build()
//        }
//    }
