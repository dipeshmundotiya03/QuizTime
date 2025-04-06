package com.example.quiztime.data.remote

import com.example.domain.util.DataError
import domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException

import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

suspend inline fun <reified T>safeCall(
    execute :() -> HttpResponse
):Result<T, DataError>{
    val response =try {
       execute()
    }catch (e: UnknownHostException){
        return Result.Failure(DataError.NoInternet)
    }catch (e: UnresolvedAddressException){
        return Result.Failure(DataError.NoInternet)
    }catch (e: SocketTimeoutException){
        return Result.Failure(DataError.RequestTimeOut)
    }catch (e: Exception){
        e.printStackTrace()
        return Result.Failure(DataError.Unknown(e.message))
    }
    return when(response.status.value){
        in 200..299 ->{
            try {
                Result.Success(response.body<T>())
            }catch (e: JsonConvertException){
                Result.Failure(DataError.Serialization)
            }catch (e: NoTransformationFoundException){
                Result.Failure(DataError.Serialization)
            }catch (e: Exception){
                e.printStackTrace()
                return Result.Failure(DataError.Unknown(e.message))
            }
        }
        408 -> Result.Failure(DataError.RequestTimeOut)
        429 -> Result.Failure(DataError.TooManyRequests)
        in 500..599 -> Result.Failure(DataError.Server)
        else -> Result.Failure(DataError.Unknown())
    }
}