package com.example.common.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.isValidEmail() =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.toDate() =
    LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))