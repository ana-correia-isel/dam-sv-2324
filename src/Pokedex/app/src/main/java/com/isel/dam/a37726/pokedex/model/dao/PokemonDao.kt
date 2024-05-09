package com.isel.dam.a37726.pokedex.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.isel.dam.a37726.pokedex.model.data.Pokemon
import com.isel.dam.a37726.pokedex.model.data.PokemonRegion
import com.isel.dam.a37726.pokedex.model.data.RegionWithPokemons

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    fun getPokemons() : List<Pokemon>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemon: Pokemon)
    @Query("SELECT COUNT(*) FROM pokemon")
    fun count(): Int

    @Transaction
    @Query("SELECT * FROM pokemon_region WHERE region_id = :regionId")
    fun getPokemonByRegion(regionId: Int): RegionWithPokemons
}