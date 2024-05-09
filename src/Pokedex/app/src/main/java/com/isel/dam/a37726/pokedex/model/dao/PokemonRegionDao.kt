package com.isel.dam.a37726.pokedex.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.isel.dam.a37726.pokedex.model.data.PokemonRegion

@Dao
interface PokemonRegionDao {
    @Query("SELECT * FROM pokemon_region")
    fun getRegions() : List<PokemonRegion>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegion(region: PokemonRegion)
    @Query("SELECT COUNT(*) FROM pokemon_region")
    fun count(): Int
}