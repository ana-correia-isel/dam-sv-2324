package com.isel.dam.a37726.pokedex.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.isel.dam.a37726.pokedex.R
import com.isel.dam.a37726.pokedex.databinding.ActivityPokemonlistBinding
import com.isel.dam.a37726.pokedex.model.data.PokemonRegion
import com.isel.dam.a37726.pokedex.model.database.DBModule
import com.isel.dam.a37726.pokedex.model.mock.MockData
import com.isel.dam.a37726.pokedex.ui.Adapters.PokemonsAdapter
import com.isel.dam.a37726.pokedex.ui.Adapters.RegionAdapter
import com.isel.dam.a37726.pokedex.ui.viewModels.PokemonListViewModel
import com.isel.dam.a37726.pokedex.ui.viewModels.RegionsViewModel


class PokemonListActivity : AppCompatActivity() {
    val viewModel: PokemonListViewModel by viewModels()
    lateinit var binding : ViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemonlist)
        val regionBinding = binding as ActivityPokemonlistBinding
        var listView = regionBinding.pksRecyclerView
        viewModel.initViewMode(DBModule.getInstance(this).pokemonRepository)

        val selectedRegion = intent.getParcelableExtra<PokemonRegion>("region")

        viewModel.pokemons.observe(this) {
            listView.adapter = it?.let { it1 ->
                PokemonsAdapter(
                    pokemonList = it1,
                    itemClickedListener = { region ->
                        startActivity(
                            Intent(
                                this,
                                PokemonDetailActivity::class.java
                            )
                        )
                    },
                    context = this
                )
            }
        }
        selectedRegion?.let { viewModel.fetchPokemons(it) }
    }
}