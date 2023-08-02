package com.issever.cryptodiary.ui.splash.fragments.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.issever.cryptodiary.data.model.OnBoardingEntity
import com.issever.cryptodiary.databinding.ItemOnBoardingBinding

class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    class OnBoardingViewHolder(val binding: ItemOnBoardingBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<OnBoardingEntity>() {
        override fun areItemsTheSame(
            oldItem: OnBoardingEntity, newItem: OnBoardingEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: OnBoardingEntity, newItem: OnBoardingEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return OnBoardingViewHolder(ItemOnBoardingBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.binding.apply {

            val context = this.root.context

            item?.let {
                tvTitle.text = context.getString(it.title)
                tvDescription.text = context.getString(it.description)
                ivIcon.setImageDrawable(ContextCompat.getDrawable(context, it.icon))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}