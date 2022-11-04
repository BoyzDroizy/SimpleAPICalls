package com.boyzdroizy.simple_api_calls.network.interfaces

import com.boyzdroizy.simple_api_calls.network.models.ErrorResponse
import com.boyzdroizy.simple_api_calls.network.models.Response

class HttpCallback<T>(
    val success: (response: Response<T>) -> Unit,
    val failed: (e: ErrorResponse) -> Unit
)