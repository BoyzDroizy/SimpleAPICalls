package com.boyzdroizy.simple_api_calls.network

import android.os.Handler
import android.os.Looper
import com.boyzdroizy.simple_api_calls.SimpleHttpRequest
import com.boyzdroizy.simple_api_calls.network.interfaces.HttpCallback
import com.boyzdroizy.simple_api_calls.network.models.Api
import com.boyzdroizy.simple_api_calls.network.models.ErrorResponse
import com.boyzdroizy.simple_api_calls.network.models.Response
import com.boyzdroizy.simple_api_calls.utils.Utils.deserialize
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.util.concurrent.Executors

class RequestManager<T>(
    private val simpleHttpRequest: SimpleHttpRequest,
    private val responseModel: Class<T>
) {

    fun request(apiCall: Api, httpCallback: HttpCallback<T>) {
        Executors
            .newSingleThreadExecutor()
            .execute {
                val result = simpleHttpRequest.executeRequest(apiCall)
                result?.let { connection ->
                    val responseCode = connection.responseCode
                    handleConnection(responseCode, connection, httpCallback)
                }
            }
    }

    private fun handleConnection(
        responseCode: Int,
        connection: HttpURLConnection,
        httpCallback: HttpCallback<T>
    ) {
        Handler(Looper.getMainLooper()).post {
            when (responseCode) {
                in 200..299 -> {
                    val data = deserialize(getResponse(connection.inputStream), responseModel)
                    httpCallback.success(Response(responseCode, data))
                }
                else -> {
                    httpCallback.failed(ErrorResponse(responseCode, Throwable(getResponse(connection.errorStream))))
                }
            }
        }
    }

    private fun getResponse(responseStream: InputStream?): String {
        val response = StringBuilder()
        if (responseStream != null) {
            try {
                var line: String?
                val br = BufferedReader(InputStreamReader(responseStream))
                while (br.readLine().also { line = it } != null) {
                    response.append(line)
                }
            } catch (e: Exception) {
                return e.localizedMessage.orEmpty()
            }
        }
        return response.toString()
    }
}