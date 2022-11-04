package com.boyzdroizy.simple_api_calls.utils

import com.google.gson.Gson
import org.json.JSONObject

object Utils {

    fun <T> deserialize(jsonString: String?, clazz: Class<T>): T? {
        val gson = Gson()
        return gson.fromJson(jsonString, clazz)
    }

    fun <T> serialize(clazz: T): JSONObject {
        return JSONObject(Gson().toJson(clazz))
    }
}