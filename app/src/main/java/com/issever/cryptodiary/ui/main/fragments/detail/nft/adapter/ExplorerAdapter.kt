package com.issever.cryptodiary.ui.main.fragments.detail.nft.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.issever.cryptodiary.data.model.entities.ExplorerEntity
import com.issever.cryptodiary.databinding.ItemNftExplorerBinding

class ExplorerAdapter : RecyclerView.Adapter<ExplorerAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemNftExplorerBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<ExplorerEntity>() {
        override fun areItemsTheSame(
            oldItem: ExplorerEntity, newItem: ExplorerEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ExplorerEntity, newItem: ExplorerEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemNftExplorerBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            item?.let {
                data = it
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}