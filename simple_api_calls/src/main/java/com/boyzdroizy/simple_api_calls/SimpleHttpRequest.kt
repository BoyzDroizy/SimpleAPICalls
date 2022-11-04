package com.boyzdroizy.simple_api_calls

import com.boyzdroizy.simple_api_calls.network.models.Api
import com.boyzdroizy.simple_api_calls.network.models.HTTPMethods
import java.io.DataOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class SimpleHttpRequest(
    private val builder: HTTPRequestBuilder
) {

    class HTTPRequestBuilder {
        internal var baseUrl: String = ""
        internal var readTimeout: Int = READ_TIMEOUT
        internal var connectTimeout: Int = CONNECT_TIMEOUT

        fun baseUrl(baseUrl: String): HTTPRequestBuilder =
            apply { this.baseUrl = baseUrl }

        fun setReadTimeout(readTimeout: Int): HTTPRequestBuilder =
            apply { this.readTimeout = readTimeout }

        fun setConnectTimeout(connectTimeout: Int): HTTPRequestBuilder =
            apply { this.connectTimeout = connectTimeout }

        fun build() = SimpleHttpRequest(builder = this@HTTPRequestBuilder)
    }

    fun executeRequest(apiCall: Api): HttpURLConnection? {
        try {
            val endpointURL = (builder.baseUrl + apiCall.url)
            val connection = createPostConnection(endpointURL, apiCall)

            if (apiCall.params != null) {
                val os = connection.outputStream
                val writer = DataOutputStream(connection.outputStream)
                writer.writeBytes(apiCall.params.toString())
                writer.flush()
                writer.close()
                os.close()
            }
            return connection
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    private fun createPostConnection(requestURL: String, apiCall: Api): HttpURLConnection {
        val url = URL(requestURL)
        val connection = url.openConnection() as HttpURLConnection

        with(connection) {
            readTimeout = builder.readTimeout
            connectTimeout = builder.connectTimeout
            requestMethod = apiCall.httpMethod.name
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Authorization", apiCall.authorization)
            apiCall.requestProperties.entries.forEach {
                setRequestProperty(it.key, it.value)
            }
            if (apiCall.httpMethod == HTTPMethods.POST) {
                doInput = true
                doOutput = true
            }
        }
        return connection
    }

    companion object {
        private const val READ_TIMEOUT = 10000
        private const val CONNECT_TIMEOUT = 30000
    }
}