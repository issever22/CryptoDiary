package com.issever.cryptodiary.ui.main.fragments.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.databinding.ItemFavoriteCoinListBinding
import java.util.Locale

class FavoriteCoinListAdapter : RecyclerView.Adapter<FavoriteCoinListAdapter.CoinListViewHolder>(), Filterable {
    class CoinListViewHolder(val binding: ItemFavoriteCoinListBinding) :
        RecyclerView.ViewHolder(binding.root)

    var fullList = ArrayList<CoinEntity>()
    var filteredList = ArrayList<CoinEntity>()

    private val differCallback = object : DiffUtil.ItemCallback<CoinEntity>() {
        override fun areItemsTheSame(
            oldItem: CoinEntity, newItem: CoinEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CoinEntity, newItem: CoinEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CoinListViewHolder(ItemFavoriteCoinListBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            item?.let {
                coin = item
                root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((CoinEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (CoinEntity) -> Unit) {
        onItemClickListener = listener
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredList = if (charSearch.isEmpty()) {
                    fullList
                } else {
                    val resultList = ArrayList<CoinEntity>()
                    fullList.forEach { coin ->
                        if (coin.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                            || coin.currentPrice.toString().lowercase(Locale.ROOT)
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
                filteredList = results?.values as ArrayList<CoinEntity>
                differ.submitList(filteredList)
            }
        }
    }
}