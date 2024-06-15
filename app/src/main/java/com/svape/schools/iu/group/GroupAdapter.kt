package com.svape.schools.iu.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svape.schools.R
import com.svape.schools.data.Group

class GroupAdapter(
    private val groups: List<Group>,
    private val onItemClick: (Group) -> Unit
) : RecyclerView.Adapter<GroupViewHolder>() {
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grupo, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groups[position], onItemClick)
    }

    override fun getItemCount(): Int = groups.size
}