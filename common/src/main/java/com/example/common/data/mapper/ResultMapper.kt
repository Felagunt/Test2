package com.example.common.data.mapper

fun interface ResultMapper<T, R> {
    fun map(input: T): R
}