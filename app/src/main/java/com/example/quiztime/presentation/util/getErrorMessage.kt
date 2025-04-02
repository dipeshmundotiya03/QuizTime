package com.example.quiztime.presentation.util

import com.example.domain.util.DataError

fun DataError.getErrorMessage(): String{
   return when(this){
        DataError.NoInternet -> "No InterNet Connection ,Check your Internet"
        DataError.RequestTimeOut -> "Request Time Out ,Please try again later"
        DataError.Serialization -> "Failed to load data ,Please try again later"
        DataError.Server ->"Server Error ,Please try again later"
        DataError.TooManyRequests -> "Too Many Requests ,Please try again later"
        is DataError.Unknown -> "Unknown Error ,${this.errorMessage}"
    }
}