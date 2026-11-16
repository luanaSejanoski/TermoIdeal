package com.example.myapplication.data.remote

import retrofit2.Retrofit

object RetrofitClient {
    val api: PalavrasApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.dicionario-aberto.net/")
            .build()
            .create(PalavrasApi::class.java)
    }
}