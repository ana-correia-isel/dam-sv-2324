package com.isel.dam.a37726.pokedex.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isel.dam.a37726.pokedex.model.data.Pokemon
import com.isel.dam.a37726.pokedex.model.data.PokemonRegion
import com.isel.dam.a37726.pokedex.model.mock.MockData
import com.isel.dam.a37726.pokedex.model.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel() {
    private val _pokemons = MutableLiveData<List<Pokemon>?>()
    val pokemons: LiveData<List<Pokemon>?>
        get() = _pokemons

    fun fetchPokemons(region: PokemonRegion) {
        viewModelScope.launch(Dispatchers.Default) {
            val response = NetworkModule.client.fetchPokemonByRegionId(region.id)

            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/1.png"

            val pkList = response.pokemons.map {

                val regexToGetId = "\\/([^\\/]+)\\/?\$".toRegex()
                var pkId = regexToGetId.find(it.url!!)?.value
                pkId = pkId?.removeSurrounding("/")
                val pkName = it.name ?: ""
                val pkIdInt = pkId?.toInt() ?: 0

                val pkImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                        "/sprites/pokemon/other/official-artwork/${pkId}.png"


                Pokemon(pkIdInt, pkName, pkImageUrl)
            }
            _pokemons.postValue(pkList)
        }
    }
}