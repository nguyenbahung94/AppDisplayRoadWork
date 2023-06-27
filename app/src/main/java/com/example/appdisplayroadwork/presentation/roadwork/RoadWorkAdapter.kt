package com.example.appdisplayroadwork.presentation.roadwork

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.example.appdisplayroadwork.databinding.ItemRoadWorkBinding


class RoadWorkAdapter(
    val onItemClicked: (MarkerViewState) -> Unit
) : ListAdapter<MarkerViewState, RoadWorkAdapter.HolderMarker>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<MarkerViewState>() {

                override fun areItemsTheSame(
                    oldItem: MarkerViewState,
                    newItem: MarkerViewState
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: MarkerViewState,
                    newItem: MarkerViewState
                ): Boolean = oldItem == newItem

            }
    }

    fun submitData(data: List<MarkerViewState>) {
        submitList(data)
    }


    class HolderMarker(val binding: ItemRoadWorkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MarkerViewState) {
            with(binding) {
                tvTitle.text = item.title
                if (item.mainStreet.isEmpty()) {
                    tvMainStreet.isVisible = false
                } else {
                    tvMainStreet.isVisible = true
                    tvMainStreet.text = "Main Street: ${item.mainStreet}"
                }
                if (item.suburb.isEmpty()) {
                    tvSubnub.isVisible = false
                } else {
                    tvSubnub.isVisible = true
                    tvSubnub.text = "Subnub: ${item.suburb}"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMarker {
        val binding =
            ItemRoadWorkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val vh = HolderMarker(binding)
        binding.root.setOnClickListener {
            onItemClicked.invoke(getItem(vh.adapterPosition))
        }
        return vh
    }


    override fun onBindViewHolder(holder: HolderMarker, position: Int) {
        holder.bind(getItem(position))
    }
}