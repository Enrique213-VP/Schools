package com.svape.schools.iu.site

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.svape.schools.R
import com.svape.schools.data.Site

class SiteViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val siteName: TextView = view.findViewById(R.id.sede_name)

    fun bind(site: Site, onItemClick: (Site) -> Unit) {
        siteName.text = site.name
        itemView.setOnClickListener { onItemClick(site) }
    }
}