package com.isel.dam.a37726.pokedex.ui.Adapters

import android.content.Context
import android.graphics.Region
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.isel.dam.a37726.pokedex.R
import com.isel.dam.a37726.pokedex.databinding.ItemRegionBinding
import com.isel.dam.a37726.pokedex.model.data.PokemonRegion

typealias OnItemClickedListener = (arg: Any?) -> Unit

class RegionAdapter(
    private val pkRegionList: List<PokemonRegion>,
    private val itemClickedListener: OnItemClickedListener? = null,
    private val context: Context
) : RecyclerView.Adapter<RegionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val regionItemBinding = ItemRegionBinding.bind(itemView)

        fun bindView(region: PokemonRegion, itemClickedListener: OnItemClickedListener?) {
            regionItemBinding.region = region
            itemView.setOnClickListener{
                itemClickedListener?.invoke(region)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_region, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val region = pkRegionList[position]
        holder.bindView(region, itemClickedListener)
    }

    override fun getItemCount(): Int {
        return pkRegionList.size
    }
}