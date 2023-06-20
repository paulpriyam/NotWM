package com.example.forgetnoting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.forgetnoting.R
import com.example.forgetnoting.databinding.LayoutReminderItemBinding
import com.example.forgetnoting.model.Reminder
import com.example.forgetnoting.util.toDateTimeString

class ReminderAdapter(private val context: Context) :
    RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    private var reminderList: ArrayList<Reminder> = arrayListOf()

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        return ReminderViewHolder(
            LayoutReminderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return reminderList.size
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminderList.get(position)
        holder.bind(reminder)
    }

    fun setData(reminders: List<Reminder>) {
        reminderList.clear()
        reminderList.addAll(reminders)
        notifyDataSetChanged()
    }
}