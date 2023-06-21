package com.example.forgetnoting.reminder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.DifferCallback
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.forgetnoting.databinding.LayoutReminderItemBinding
import com.example.forgetnoting.reminder.model.Reminder
import com.example.forgetnoting.util.toDateTimeString

class ReminderListPagingDataAdapter :
    PagingDataAdapter<Reminder, ReminderListPagingDataAdapter.ReminderViewHolder>(diffCallback = diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Reminder>() {
            override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ReminderViewHolder(private val binding: LayoutReminderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reminder: Reminder) {
            binding.tvReminderTitle.text = reminder.title
            binding.tvReminderDesc.text = reminder.description
            binding.tvRemindAt.text = reminder.remindAt
            binding.tvCreatedAt.text = reminder.createdAt?.toDateTimeString()
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    reminder.color
                )
            )
        }

    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = getItem(position)
        if (reminder != null) {
            holder.bind(reminder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        return ReminderViewHolder(
            LayoutReminderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}