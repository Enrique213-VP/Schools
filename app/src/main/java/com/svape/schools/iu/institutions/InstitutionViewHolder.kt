package com.svape.schools.iu.institutions

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.svape.schools.R
import com.svape.schools.data.Institution

class InstitutionViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val institutionName: TextView = view.findViewById(R.id.institucion_name)

    fun bind(institution: Institution, onItemClick: (Institution) -> Unit) {
        institutionName.text = institution.name
        itemView.setOnClickListener { onItemClick(institution) }
    }
}