package com.issever.cryptodiary.ui.main.fragments.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.issever.cryptodiary.data.model.entities.TrendingCoinEntity
import com.issever.cryptodiary.databinding.ItemTrendingCoinListBinding
import java.util.Locale

class TrendingCoinListAdapter : RecyclerView.Adapter<TrendingCoinListAdapter.TrendingCoinListViewHolder>(), Filterable {
    class TrendingCoinListViewHolder(val binding: ItemTrendingCoinListBinding) :
        RecyclerView.ViewHolder(binding.root)

    var fullList = ArrayList<TrendingCoinEntity>()
    var filteredList = ArrayList<TrendingCoinEntity>()

    private val differCallback = object : DiffUtil.ItemCallback<TrendingCoinEntity>() {
        override fun areItemsTheSame(
            oldItem: TrendingCoinEntity, newItem: TrendingCoinEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: TrendingCoinEntity, newItem: TrendingCoinEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingCoinListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TrendingCoinListViewHolder(ItemTrendingCoinListBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: TrendingCoinListViewHolder, position: Int) {
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

    private var onItemClickListener: ((TrendingCoinEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (TrendingCoinEntity) -> Unit) {
        onItemClickListener = listener
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredList = if (charSearch.isEmpty()) {
                    fullList
                } else {
                    val resultList = ArrayList<TrendingCoinEntity>()
                    fullList.forEach { coin ->
                        if (coin.item.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                            || coin.item.priceBtc.toString().lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                            || coin.item.symbol.lowercase(Locale.ROOT)
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
                filteredList = results?.values as ArrayList<TrendingCoinEntity>
                differ.submitList(filteredList)
            }
        }
    }
}