package com.example.courses.data.network.handler

import com.example.common.utils.Either
import com.example.common.utils.Failure
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toEither(): Either<Failure, Nothing> {
    return when (this) {
        is IOException -> Either.Left(Failure.NetworkError(this))
        is HttpException -> {
            val code = code()
            val message = message()
            Either.Left(Failure.ServerError(code, message))
        }
        else -> Either.Left(Failure.UnknownError(this))
    }
}