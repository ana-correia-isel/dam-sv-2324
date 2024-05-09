package com.isel.dam.a37726.pokedex.model.database

import android.annotation.SuppressLint
import android.content.Context
import com.isel.dam.a37726.pokedex.model.network.HttpRequestInterceptor
import com.isel.dam.a37726.pokedex.model.network.NetworkModule
import com.isel.dam.a37726.pokedex.model.network.PokemonApi
import com.isel.dam.a37726.pokedex.model.repositories.PokemonRepository
import com.isel.dam.a37726.pokedex.model.repositories.RegionRepository
import okhttp3.OkHttpClient

class DBModule(private val context:Context) {

    val pokemonClient: PokemonApi

    val regionRepository : RegionRepository

    val pokemonDBManager : PokemonDatabase

    var pokemonRepository: PokemonRepository

    companion object {
        // For Singleton instantiation
        @Volatile private var instance : DBModule ? = null
        fun getInstance (context : Context): DBModule {
            if ( instance != null ) return instance !!
            synchronized ( this ) {
                return DBModule(context)
            }
            return instance!!
        }
    }

    init {
        pokemonClient = NetworkModule.initPokemonRemoteService()
        pokemonDBManager = PokemonDatabase.getInstance(context)
        regionRepository = RegionRepository(pokemonClient,pokemonDBManager.regionDao())
        pokemonRepository = PokemonRepository(pokemonClient,pokemonDBManager.pokemonDao())
    }
}