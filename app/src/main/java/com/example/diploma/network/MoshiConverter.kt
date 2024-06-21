package com.example.diploma.network

import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class MoshiConverter(private val moshi: Moshi) {

    @kotlin.jvm.Throws(RuntimeException::class)
    fun fromJson(clazz: Class<*>, jsonString: String): Any? {
        return moshi.adapter(clazz).fromJson(jsonString)
    }

    @kotlin.jvm.Throws(RuntimeException::class)
    fun fromJson(type: Type, jsonString: String): Any? {
        return moshi.adapter<Any>(type).fromJson(jsonString)
    }
}
