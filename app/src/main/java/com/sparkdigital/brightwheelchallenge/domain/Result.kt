package com.sparkdigital.brightwheelchallenge.domain

sealed class Result<out T: Any> {
    data class Success<out T: Any>(val value: T): Result<T>()
    data class Complete(val value: String?) : Result<Nothing>()
    data class Failure(val err: Throwable?): Result<Nothing>()
}
