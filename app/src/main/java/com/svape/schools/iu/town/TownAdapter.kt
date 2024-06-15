package com.svape.schools.iu.town

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svape.schools.R
import com.svape.schools.data.Town

class TownAdapter(
    private val towns: List<Town>,
    private val onItemClick: (Town) -> Unit
) : RecyclerView.Adapter<TownViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TownViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_municipio, parent, false)
        return TownViewHolder(view)
    }

    override fun onBindViewHolder(holder: TownViewHolder, position: Int) {
        holder.bind(towns[position], onItemClick)
    }

    override fun getItemCount(): Int = towns.size
}