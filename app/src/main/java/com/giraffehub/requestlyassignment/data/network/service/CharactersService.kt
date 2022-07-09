package com.giraffehub.requestlyassignment.data.network.service

import com.giraffehub.requestlyassignment.data.network.model.Character
import com.giraffehub.requestlyassignment.data.network.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharactersResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character
}