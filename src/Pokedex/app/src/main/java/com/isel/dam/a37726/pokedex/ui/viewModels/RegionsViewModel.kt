package com.isel.dam.a37726.pokedex.ui.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isel.dam.a37726.pokedex.model.data.Pokemon
import com.isel.dam.a37726.pokedex.model.data.PokemonDetail
import com.isel.dam.a37726.pokedex.model.data.PokemonRegion
import com.isel.dam.a37726.pokedex.model.mock.MockData
import com.isel.dam.a37726.pokedex.model.network.NetworkModule
import com.isel.dam.a37726.pokedex.model.repositories.RegionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegionsViewModel : ViewModel() {
    private val _regions = MutableLiveData<List<PokemonRegion>?>()
    val regions: LiveData<List<PokemonRegion>?>
        get() = _regions

    private lateinit var _repository: RegionRepository
    fun initViewMode(repository: RegionRepository) {
        _repository = repository
    }
    fun fetchRegions() {

        //_regions.value = MockData.regions
        viewModelScope.launch(Dispatchers.Default) {
            val regionsList = _repository.getRegions()
            _regions.postValue(regionsList.value)
        }
    }
}

