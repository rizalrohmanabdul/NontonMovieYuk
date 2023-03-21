package com.rizal.nontonmovieyuk.utils


import com.rizal.nontonmovieyuk.utils.network.ErrorResponse
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody

object ExceptionUtil {

    fun ResponseBody?.convertErrorBody(): ErrorResponse {
        return try {
            this?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            } ?: ErrorResponse(998, "Unknown error has occurred. Please try again later")
        } catch (exception: Exception) {
            ErrorResponse(999, exception.message)
        }
    }

    fun ErrorResponse.getMessage(): String {
        return message ?: "Unknown error has occurred. Please try again later"
    }

    fun ResponseBody?.toException(): Exception {
        return Exception(this.convertErrorBody().getMessage())
    }
}