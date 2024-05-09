package com.isel.dam.a37726.pokedex.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@kotlinx.parcelize.Parcelize
@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey
    @ColumnInfo(name = "pokemon_id")
    var id: Int,

    @ColumnInfo(name = "pokemon_name")
    var name:String,

    @ColumnInfo(name = "pokemon_imgUrl")
    var imageUrl: String,

    @ColumnInfo(name = "region_id")
    var regionId: Int? = null
) : Parcelable

data class RegionWithPokemons(
    @Embedded
    val region: PokemonRegion,

    @Relation(
        parentColumn = "region_id",
        entityColumn = "region_id"
    )
    val pokemon: List<Pokemon>
)