package com.boyzdroizy.simple_api_calls.network.models

data class ErrorResponse(
    var code: Int,
    var throwable: Throwable? = null
)
