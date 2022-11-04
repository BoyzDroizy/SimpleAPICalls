package com.boyzdroizy.simple_api_calls.network.models

import com.boyzdroizy.simple_api_calls.utils.Utils.serialize
import org.json.JSONObject

data class Api(
    val httpMethod: HTTPMethods,
    val url: String,
) {

    internal var params: JSONObject? = null
        private set
    internal var authorization: String = ""
        private set
    internal var requestProperties: HashMap<String, String> = hashMapOf()
        private set

    fun <T> setParams(clazz: T) {
        this.params = serialize(clazz)
    }

    fun setParams(params: JSONObject) {
        this.params = params
    }

    fun setRequestProperties(requestProperties: HashMap<String, String>) {
        this.requestProperties = requestProperties
    }

    fun setAuthorization(authorization: String) {
        this.authorization = authorization
    }
}

