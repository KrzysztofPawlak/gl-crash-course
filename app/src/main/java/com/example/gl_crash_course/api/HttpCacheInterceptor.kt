package com.example.gl_crash_course.api

import android.content.Context
import com.example.gl_crash_course.R
import com.example.gl_crash_course.service.NetworkUtils
import okhttp3.*
import java.io.IOException

class HttpCacheInterceptor(var context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request().newBuilder()

        if (!NetworkUtils.hasNetwork(context)!!) {
            return Response.Builder()
                .code(418)
                .protocol(Protocol.HTTP_2)
                .message(context.getString(R.string.no_network_response_message))
                .body(ResponseBody.create(MediaType.get("text/html; charset=utf-8"), ""))
                .request(chain.request()).build()
        }

        return chain.proceed(request.build())
    }

}