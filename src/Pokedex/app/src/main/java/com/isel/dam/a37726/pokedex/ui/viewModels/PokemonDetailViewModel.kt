package com.isel.dam.a37726.pokedex.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isel.dam.a37726.pokedex.model.data.Pokemon
import com.isel.dam.a37726.pokedex.model.data.PokemonDetail
import com.isel.dam.a37726.pokedex.model.mock.MockData

class PokemonDetailViewModel : ViewModel() {
    private val _pokemonDetail = MutableLiveData<PokemonDetail>()
    val pokemonDetail: LiveData<PokemonDetail>
        get() = _pokemonDetail

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon


    fun fetchPokemonDetail(pokemon: Pokemon) {
        this._pokemon.value = pokemon
        this._pokemonDetail.value = MockData.pokemonDetail[0]
    }
}