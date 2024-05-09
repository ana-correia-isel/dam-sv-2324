package com.isel.dam.a37726.pokedex.model.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.isel.dam.a37726.pokedex.model.dao.PokemonDao
import com.isel.dam.a37726.pokedex.model.data.Pokemon
import com.isel.dam.a37726.pokedex.model.data.PokemonRegion
import com.isel.dam.a37726.pokedex.model.network.PokemonApi

class PokemonRepository(private val pokemonApi: PokemonApi,
                        private val pokemonDao: PokemonDao) {

    suspend fun getPokemonsByRegion(region: PokemonRegion): LiveData<List<Pokemon>> {
        try {

            var regionWithPokemons = pokemonDao.getPokemonByRegion(region.id)

            if (regionWithPokemons.pokemon.isEmpty()) {
                var pkByRegionResponse = pokemonApi.fetchPokemonByRegionId(region.id)
                val pokemons = pkByRegionResponse.pokemons.map {
                    val regexToGetId = "/([^/]+)/?\$".toRegex()
                    var pkId = regexToGetId.find(it.url!!)?.value
                    pkId = pkId?.removeSurrounding("/")
                    val pkName = it.name ?: ""
                    val pkIdInt = pkId?.toInt() ?: 0
                    val pkImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                            "/sprites/pokemon/other/official-artwork/${pkId}.png"
                    Pokemon(pkIdInt, pkName, pkImageUrl, regionId = region.id)
                }
                savePokemonsinDB(pokemons)
                return MutableLiveData(pokemons)
            } else {
                val pks = regionWithPokemons.pokemon
                return MutableLiveData(pks)
            }
        } catch (e: java.lang.Exception) {
            Log.e("ERROR", e.toString())
        }

        return MutableLiveData<List<Pokemon>>()
    }

    private fun savePokemonsinDB(pokemons: List<Pokemon>) {
        pokemons.forEach {
            it.let { it1 -> pokemonDao.insertPokemon(it1) }
        }
    }
}