package com.boyzdroizy.simple_api_calls.network.models

data class Response<T>(
    var code: Int = 0,
    var body: T? = null
)