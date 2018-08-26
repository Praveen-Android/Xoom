package com.praveen.xoom.data

import com.praveen.xoom.data.Status.*

//Helper class to pass Live Objects between Views and View Models
open class Response<out T> private constructor(val status: Status, val data: T?, val error: String?) {

    companion object {
        fun <T> loading(): Response<T> {
            return Response(LOADING, null, null)
        }

        fun <T> success(data: T): Response<T> {
            return Response(SUCCESS, data, null)
        }

        fun <T> error(error: Throwable?): Response<T> {
            return Response(ERROR, null, error?.message)
        }

        fun <T> error(data: T, error: Throwable?): Response<T> {
            return Response(ERROR, data, error?.message)
        }
    }
}
