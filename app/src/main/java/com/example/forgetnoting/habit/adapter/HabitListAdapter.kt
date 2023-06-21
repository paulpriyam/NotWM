package com.example.forgetnoting.habit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.forgetnoting.databinding.LayoutHabitItemBinding
import com.example.forgetnoting.habit.model.Habit

class HabitListAdapter : RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {

    private var habitList: ArrayList<Habit> = arrayListOf()

    inner class HabitViewHolder(private val binding: LayoutHabitItemBinding) :
        ViewHolder(binding.root) {

        fun bind(habit: Habit) {
            binding.tvHabitTitle.text = habit.title
            binding.tvCompletedDays.text = habit.daysCompleted.toString()
            binding.ivHabit.setImageBitmap(habit.image)
            binding.progress.max = habit.goal
            binding.progress.progress = habit.daysCompleted
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        return HabitViewHolder(
            LayoutHabitItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]
        holder.bind(habit)
    }

    fun setData(habits: List<Habit>) {
        habitList.clear()
        habitList.addAll(habits)
        notifyDataSetChanged()
    }
}