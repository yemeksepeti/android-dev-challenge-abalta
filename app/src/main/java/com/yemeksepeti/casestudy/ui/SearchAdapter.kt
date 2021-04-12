package com.yemeksepeti.casestudy.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yemeksepeti.casestudy.BR
import com.yemeksepeti.casestudy.databinding.ItemResultBinding
import com.yemeksepeti.casestudy.model.SearchedItem

class SearchAdapter : PagingDataAdapter<SearchedItem, SearchAdapter.ViewHolder>(diffCallback) {

    var goToDetail: (SearchedItem) -> Unit = {}

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<SearchedItem>() {
            override fun areItemsTheSame(
                oldItem: SearchedItem,
                newItem: SearchedItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SearchedItem,
                newItem: SearchedItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchedItem) {
            binding.apply {
                setVariable(BR.item, item)
                executePendingBindings()
                itemView.setOnClickListener {
                    goToDetail(item)
                }
            }
        }
    }

}