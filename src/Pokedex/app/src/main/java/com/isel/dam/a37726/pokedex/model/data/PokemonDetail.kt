package com.isel.dam.a37726.pokedex.model.data

data class PokemonDetail(
    var pokemon: Pokemon,
    var description: String,
    var types:List<PokemonType>,
    var weight:Double?,
    var height: Double?
)