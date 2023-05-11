package com.example.core.dtos.auth

class Result<A, B> private constructor(
    val success: Boolean,
    val failure: A?,
    val value: B?) {
    companion object {
        fun <A, B> success(value: B): Result<A, B> {
            return Result(true, null, value)
        }

        fun <A, B> failure(failure: A): Result<A, B> {
            return Result(false, failure, null)
        }
    }
}