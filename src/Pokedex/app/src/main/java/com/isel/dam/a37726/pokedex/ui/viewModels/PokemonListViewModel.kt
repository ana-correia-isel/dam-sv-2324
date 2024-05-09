package com.isel.dam.a37726.pokedex.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isel.dam.a37726.pokedex.model.data.Pokemon
import com.isel.dam.a37726.pokedex.model.data.PokemonRegion
import com.isel.dam.a37726.pokedex.model.mock.MockData
import com.isel.dam.a37726.pokedex.model.network.NetworkModule
import com.isel.dam.a37726.pokedex.model.repositories.PokemonRepository
import com.isel.dam.a37726.pokedex.model.repositories.RegionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel() {
    private val _pokemons = MutableLiveData<List<Pokemon>?>()
    val pokemons: LiveData<List<Pokemon>?>
        get() = _pokemons

    private lateinit var _repository: PokemonRepository
    fun initViewMode(repository: PokemonRepository) {
        _repository = repository
    }

    fun fetchPokemons(region: PokemonRegion) {
        viewModelScope.launch(Dispatchers.Default) {
           val pkList = _repository.getPokemonsByRegion(region)
            _pokemons.postValue(pkList.value)
        }
    }
}