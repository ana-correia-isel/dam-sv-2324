package com.isel.dam.a37726.pokedex.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isel.dam.a37726.pokedex.R
import com.isel.dam.a37726.pokedex.databinding.ActivityPokemonDetailBinding
import com.isel.dam.a37726.pokedex.model.data.Pokemon
import com.isel.dam.a37726.pokedex.model.mock.MockData
import com.isel.dam.a37726.pokedex.ui.Adapters.TypeAdapter
import com.isel.dam.a37726.pokedex.ui.viewModels.PokemonDetailViewModel

class PokemonDetailActivity : AppCompatActivity() {
    val viewModel: PokemonDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding: ActivityPokemonDetailBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_pokemon_detail)

        viewModel.pokemonDetail.observe(this) { newValue ->
            binding.pokemonDetail = newValue
            binding.typeListView.adapter = TypeAdapter(newValue.types,this)
        }

        viewModel.pokemon.observe(this) { newValue ->
            binding.pokemon = newValue
        }

        viewModel.fetchPokemonDetail(MockData.pokemons[10])

        binding.typeListView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,false
        )

        binding.typeListView.addItemDecoration(EqualSpacingItemDecoration(30, EqualSpacingItemDecoration.HORIZONTAL));
    }
}


class EqualSpacingItemDecoration @JvmOverloads constructor(
    private val spacing: Int,
    private var displayMode: Int = -1
) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildViewHolder(view).adapterPosition
        val itemCount = state.itemCount
        val layoutManager = parent.layoutManager
        setSpacingForDirection(outRect, layoutManager, position, itemCount)
    }

    private fun setSpacingForDirection(
        outRect: Rect,
        layoutManager: RecyclerView.LayoutManager?,
        position: Int,
        itemCount: Int
    ) {

        // Resolve display mode automatically
        if (displayMode == -1) {
            displayMode = resolveDisplayMode(layoutManager)
        }
        when (displayMode) {
            HORIZONTAL -> {
                outRect.left = spacing
                outRect.right = if (position == itemCount - 1) spacing else 0
                outRect.top = spacing
                outRect.bottom = spacing
            }
            VERTICAL -> {
                outRect.left = spacing
                outRect.right = spacing
                outRect.top = spacing
                outRect.bottom = if (position == itemCount - 1) spacing else 0
            }
            GRID -> if (layoutManager is GridLayoutManager) {
                val cols = layoutManager.spanCount
                val rows = itemCount / cols
                outRect.left = spacing
                outRect.right = if (position % cols == cols - 1) spacing else 0
                outRect.top = spacing
                outRect.bottom = if (position / cols == rows - 1) spacing else 0
            }
        }
    }

    private fun resolveDisplayMode(layoutManager: RecyclerView.LayoutManager?): Int {
        if (layoutManager is GridLayoutManager) return GRID
        return if (layoutManager!!.canScrollHorizontally()) HORIZONTAL else VERTICAL
    }

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
        const val GRID = 2
    }
}