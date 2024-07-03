package com.kubsau.regrab.network

import com.slack.eithernet.ApiException
import com.slack.eithernet.errorType
import com.slack.eithernet.toType
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ResponseConverterFactory(private val converter: MoshiConverter) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val (errorType, _) = annotations.errorType() ?: return null
        val errorRaw = getRawType(errorType.toType())
        return ResponseBodyConverter(
            successType = type,
            errorRaw = errorRaw,
            converter = converter,
        )
    }

    class ResponseBodyConverter(
        private val successType: Type,
        private val errorRaw: Class<*>,
        private val converter: MoshiConverter,
    ) : Converter<ResponseBody, Any?> {
        override fun convert(value: ResponseBody): Any? {
            val string = value.string()
            runCatching {
                converter.fromJson(successType, string)
            }.fold(
                onSuccess = { successModel -> return successModel },
                onFailure = {
                    val isUnit = successType == Unit::class.java

                    kotlin.runCatching {
                        converter.fromJson(errorRaw, string)
                    }.fold(
                        onSuccess = { errorModel -> throw ApiException(errorModel) },
                        onFailure = { if (!isUnit) throw it else return Unit }
                    )
                }
            )
        }
    }
}
