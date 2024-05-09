package com.isel.dam.a37726.pokedex.model.data

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@kotlinx.parcelize.Parcelize
@Entity(tableName = "pokemon_type")
data class PokemonType(
    @PrimaryKey
    @ColumnInfo(name = "typeId")
    var id: Int,
    @ColumnInfo(name = "type_name")
    var name:String,
    @ColumnInfo(name = "type_icon")
    @DrawableRes val icon: Int,
    @ColumnInfo(name = "type_color")
    @ColorRes val color: Int): Parcelable