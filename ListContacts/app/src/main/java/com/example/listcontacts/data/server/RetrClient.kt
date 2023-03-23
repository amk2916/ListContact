package com.example.listcontacts.data.server

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

object RetrClient {
    val retrofitClient = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val charactersService = retrofitClient.create<CharactersService>()
}

data class ServerResponse(
    val results: List<ServerCharacter>
)

interface CharactersService {

    @GET("character")
    fun getCharacters(): Call<ServerResponse>
}