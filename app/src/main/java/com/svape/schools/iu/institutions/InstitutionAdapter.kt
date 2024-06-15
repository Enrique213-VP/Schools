package com.svape.schools.iu.institutions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svape.schools.R
import com.svape.schools.data.Institution

class InstitutionAdapter(
    private val institutions: List<Institution>,
    private val onItemClick: (Institution) -> Unit
) : RecyclerView.Adapter<InstitutionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstitutionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_institucion, parent, false)
        return InstitutionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstitutionViewHolder, position: Int) {
        holder.bind(institutions[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return institutions.size
    }
}