package me.nickp0is0n.easylocalizelite.network

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.net.URL

class QueryClient (val url: URL) {
    fun doQuery(request: Any): String? {
        val mapper = ObjectMapper()
        val node = mapper.valueToTree<ObjectNode>(request)

        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = node.toPrettyString().toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        var response: Response? = null
        try {
            response = client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response?.body?.string()
    }
}