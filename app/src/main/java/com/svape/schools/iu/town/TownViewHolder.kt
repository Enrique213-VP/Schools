package com.svape.schools.iu.town

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.svape.schools.R
import com.svape.schools.data.Town

class TownViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val townName: TextView = view.findViewById(R.id.municipio_name)
    private val imageViewMunicipio: ImageView = view.findViewById(R.id.imageViewMunicipio)


    fun bind(town: Town, onItemClick: (Town) -> Unit) {
        townName.text = town.name
        imageViewMunicipio.setImageResource(R.drawable.tolima)
        itemView.setOnClickListener { onItemClick(town) }
    }
}