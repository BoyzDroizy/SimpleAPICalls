package com.boyzdroizy.simpleapicalls

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.boyzdroizy.simple_api_calls.SimpleHttpRequest
import com.boyzdroizy.simple_api_calls.network.RequestManager
import com.boyzdroizy.simple_api_calls.network.interfaces.HttpCallback
import com.boyzdroizy.simple_api_calls.network.models.Api
import com.boyzdroizy.simple_api_calls.network.models.HTTPMethods
import com.boyzdroizy.simpleapicalls.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initListeners()
    }

    private val httpRequest: SimpleHttpRequest = SimpleHttpRequest.HTTPRequestBuilder()
        .baseUrl("https://v0rmr.mocklab.io")
        .build()


    private fun initListeners() {
        binding.testApiGet.setOnClickListener {
            testGetApiCall()
        }
        binding.testApiPost.setOnClickListener {
            testPOSTApiCall()
        }
        binding.testApiPut.setOnClickListener {
            testPUTApiCall()
        }
        binding.testApiDelete.setOnClickListener {
            testDeleteApiCall()
        }
    }

    private fun testGetApiCall() {
        val callRequest = Api(
            HTTPMethods.GET,
            "/json/1"
        )

        val requestManager = RequestManager(httpRequest, TestModel::class.java)
        requestManager.request(
            callRequest,
            HttpCallback({ response ->
                binding.responseText.text = "OnSuccess: $response"
            }, { error ->
                binding.responseText.text = "onError: code ${error.code} message ${error.throwable?.message}$"
            })
        )
    }

    private fun testPOSTApiCall() {
        val callRequest = Api(
            HTTPMethods.POST,
            "/json"
        ).apply {
            setParams(TestModel(12345, "abc-def-ghi"))
        }

        val requestManager = RequestManager(httpRequest, Response::class.java)
        requestManager.request(
            callRequest,
            HttpCallback({ response ->
                binding.responseText.text = "OnSuccess: $response"
            }, { error ->
                binding.responseText.text = "onError: code ${error.code} message ${error.throwable?.message}$"
            })
        )
    }

    private fun testPUTApiCall() {
        val callRequest = Api(
            HTTPMethods.PUT,
            "/json/2"
        ).apply {
            setParams(TestModel(1234, "abc-def"))
        }

        val requestManager = RequestManager(httpRequest, Response::class.java)
        requestManager.request(
            callRequest,
            HttpCallback({ response ->
                binding.responseText.text = "OnSuccess: $response"
            }, { error ->
                binding.responseText.text = "onError: code ${error.code} message ${error.throwable?.message}$"
            })
        )
    }

    private fun testDeleteApiCall() {
        val callRequest = Api(
            HTTPMethods.DELETE,
            "/json/1"
        )

        val requestManager = RequestManager(httpRequest, Response::class.java)
        requestManager.request(
            callRequest,
            HttpCallback({ response ->
                binding.responseText.text = "OnSuccess: $response"
            }, { error ->
                binding.responseText.text = "onError: code ${error.code} message ${error.throwable?.message}$"
            })
        )
    }
}