package com.issever.cryptodiary.ui.main.fragments.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.issever.cryptodiary.data.model.entities.NftEntity
import com.issever.cryptodiary.databinding.ItemNftListBinding
import java.util.Locale

class TrendingNftListAdapter : RecyclerView.Adapter<TrendingNftListAdapter.TrendingNftListViewHolder>(), Filterable {
    class TrendingNftListViewHolder(val binding: ItemNftListBinding) :
        RecyclerView.ViewHolder(binding.root)

    var fullList = ArrayList<NftEntity>()
    var filteredList = ArrayList<NftEntity>()

    private val differCallback = object : DiffUtil.ItemCallback<NftEntity>() {
        override fun areItemsTheSame(
            oldItem: NftEntity, newItem: NftEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: NftEntity, newItem: NftEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingNftListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TrendingNftListViewHolder(ItemNftListBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: TrendingNftListViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            item?.let {
                data = item
                root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((NftEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (NftEntity) -> Unit) {
        onItemClickListener = listener
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredList = if (charSearch.isEmpty()) {
                    fullList
                } else {
                    val resultList = ArrayList<NftEntity>()
                    fullList.forEach { coin ->
                        if (coin.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                            || coin.symbol.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(coin)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<NftEntity>
                differ.submitList(filteredList)
            }
        }
    }
}