package com.example.bmicalculator.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bmicalculator.data.history.HistoryItem
import com.example.bmicalculator.databinding.ItemHistoryBinding
import java.util.Locale

class HistoryAdapter(
    private val items: List<HistoryItem>
) : RecyclerView.Adapter<HistoryAdapter.VH>() {

    inner class VH(val binding: ItemHistoryBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        holder.binding.tvBmi.text =
            String.format(Locale.US, "%.1f", item.bmi)

        holder.binding.tvCategory.text = item.category
        holder.binding.tvDate.text = item.date
    }

    override fun getItemCount() = items.size
}
