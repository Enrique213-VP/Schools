package com.svape.schools.iu.site

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svape.schools.R
import com.svape.schools.data.Site

class SiteAdapter(
    private val sites: List<Site>,
    private val onItemClick: (Site) -> Unit
) : RecyclerView.Adapter<SiteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sede, parent, false)
        return SiteViewHolder(view)
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        holder.bind( sites[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return sites.size
    }
}