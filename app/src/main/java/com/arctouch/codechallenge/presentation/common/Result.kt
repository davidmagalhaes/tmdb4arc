package com.arctouch.codechallenge.presentation.common

interface Result<T> {

    val data : T?
    val error : Throwable?

    val failed
        get() = this is ErrorResult

    val returnedData
        get() = this is DataResult

    val waiting
        get() = this is WaitingResult

    val empty
        get() = this is EmptyResult

    fun isSuccessful() = returnedData || empty
    fun hasFailed() = failed
    fun hasData() = returnedData
    fun isWaiting() = waiting
    fun isEmpty() = empty

    class ErrorResult<T>(
            override val error: Throwable?,
            override val data: T? = null
    ) : Result<T>

    class DataResult<T>(
            override val data: T?,
            override val error: Throwable? = null
    ) : Result<T>

    class EmptyResult<T>(
            override val data: T? = null,
            override val error: Throwable? = null
    ) : Result<T>

    class WaitingResult<T>(
            override val data: T? = null,
            override val error: Throwable? = null
    ) : Result<T>

    companion object {
        fun <T> withData(data : T) : Result<T> {
            return DataResult(data)
        }

        fun <T> error(error : Throwable) : Result<T> {
            return ErrorResult(error)
        }

        fun <T> empty() : Result<T> {
            return EmptyResult()
        }

        fun <T> waiting() : Result<T> {
            return WaitingResult()
        }
    }
}