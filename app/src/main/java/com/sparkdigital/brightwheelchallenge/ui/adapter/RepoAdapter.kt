package com.sparkdigital.brightwheelchallenge.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sparkdigital.brightwheelchallenge.R
import com.sparkdigital.brightwheelchallenge.domain.RepositoryItem
import com.sparkdigital.brightwheelchallenge.utils.formatNumber
import java.lang.Integer.max
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoAdapter: RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private val list = mutableListOf<RepositoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false))

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        list.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = list.size
    fun addItems(items: List<RepositoryItem>) {
        list.addAll(items)
        notifyItemRangeInserted(max((list.size-items.size), 0), items.size)
    }

    fun updateItem(item: RepositoryItem?) {
        item?.let { newItem ->
            list.indexOfFirst { newItem == it }.apply {
                list[this] = newItem
                notifyItemChanged(this, newItem)
            }
        }
    }

    inner class RepoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: RepositoryItem) {
            itemView.name.text = item.name
            itemView.top_contributor.text =
                itemView.context.getString(
                    R.string.contributor_label,
                    if (item.topContributor.isEmpty()) {
                        itemView.context.getString(R.string.no_contributor)
                    } else {
                        item.topContributor
                    }
                )
            itemView.stars.text = item.stars.formatNumber()
        }
    }

}