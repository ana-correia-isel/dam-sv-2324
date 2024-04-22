package com.isel.dam.a37726.pokedex.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.isel.dam.a37726.pokedex.R
import com.isel.dam.a37726.pokedex.databinding.ActivityRegionsBinding
import com.isel.dam.a37726.pokedex.model.data.PokemonRegion
import com.isel.dam.a37726.pokedex.model.mock.MockData
import com.isel.dam.a37726.pokedex.ui.Adapters.RegionAdapter
import com.isel.dam.a37726.pokedex.ui.viewModels.RegionsViewModel

class RegionsActivity : BottomNavActivity() {
    val viewModel: RegionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val regionBinding = binding as ActivityRegionsBinding
        var listView = regionBinding.regionsRecyclerView

        viewModel.regions.observe(this) {
            listView.adapter = it?.let { it1 ->
                RegionAdapter(
                    pkRegionList = it1,
                    itemClickedListener = { region ->
                        val intent = Intent(
                            this,
                            PokemonListActivity::class.java
                        )

                        intent.putExtra("region", region as PokemonRegion)
                        startActivity(
                            intent
                        )
                    },
                    context = this
                )
            }
        }

        viewModel.fetchRegions()

        /*listView.adapter = RegionAdapter(pkRegionList = MockData.regions,
            itemClickedListener = { region->
                startActivity(Intent(this,
                    PokemonListActivity::class.java))
            },
            context = this)*/
    }

    override val contentViewId: Int
        get() = R.layout.activity_regions
    override val navigationMenuItemId: Int
        get() = R.id.navigation_regions
}