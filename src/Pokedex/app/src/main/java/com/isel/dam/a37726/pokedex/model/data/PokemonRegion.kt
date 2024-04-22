package com.isel.dam.a37726.pokedex.model.data

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class PokemonRegion(
    var id: Int,
    var name: String
): Parcelable