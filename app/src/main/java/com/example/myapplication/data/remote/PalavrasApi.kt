package com.example.myapplication.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PalavrasApi {
    @GET("word/{palavra}")
    suspend fun buscarPalavra(@Path("palavra") palavra: String): Response<ResponseBody>
}