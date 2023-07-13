package com.example.ive.network

import com.google.gson.Gson
import retrofit2.Response

private typealias RepositoryRequest<T> = suspend () -> Response<T>

abstract class BaseRepository {

    private val gson:Gson = Gson()

    suspend fun <T> request(execute:RepositoryRequest<T>):ApiResponse<T>{
        return try {
            val response = execute()
            if(response.isSuccessful){
                response.body()?.let {
                    ApiResponse.Success(it)
                } ?: ApiResponse.Error("Data not found")
            } else {
                val errorData =
                    gson.fromJson(response.errorBody()?.charStream(),ErrorResponse::class.java)
                ApiResponse.Error(errorData.errors.first())
            }
        } catch (e: Throwable){
            ApiResponse.Error(e.message ?: "Something went wrong")
        }
    }

}