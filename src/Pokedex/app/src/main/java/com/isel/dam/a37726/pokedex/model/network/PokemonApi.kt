package com.isel.dam.a37726.pokedex.model.network

import com.isel.dam.a37726.pokedex.model.network.responses.PokemonByRegionResponse
import com.isel.dam.a37726.pokedex.model.network.responses.PokemonListBaseResponse
import com.isel.dam.a37726.pokedex.model.network.responses.PokemonRegionsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi
{
    @GET("region")
    suspend fun fetchRegionList(): PokemonListBaseResponse<PokemonRegionsResponse>

    @GET("generation/{id}")
    suspend fun fetchPokemonByRegionId(@Path("id") id:Int): PokemonByRegionResponse

   /* @GET("pokemon/{id}")
    suspend fun fetchPokemonDetailById(@Path("id") id:Int): PokemonDetailResponse

    @GET("type")
    suspend fun fetchPokemonTypes(): PokemonListBaseResponse<PokemonGenericResponse>
*/
}