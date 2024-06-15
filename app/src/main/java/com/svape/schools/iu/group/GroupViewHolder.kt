package com.svape.schools.iu.group

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.svape.schools.R
import com.svape.schools.data.Group

class GroupViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val groupName: TextView = itemView.findViewById(R.id.grupo_name)
    private val numGroup: TextView = itemView.findViewById(R.id.num_grupo)

    fun bind(group: Group, onItemClick: (Group) -> Unit) {
        groupName.text = group.name
        numGroup.text = group.numGroup
        itemView.setOnClickListener { onItemClick(group) }
    }
}