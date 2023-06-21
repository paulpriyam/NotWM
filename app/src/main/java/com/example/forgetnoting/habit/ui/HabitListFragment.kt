package com.example.forgetnoting.habit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forgetnoting.R
import com.example.forgetnoting.databinding.FragmentHabitListBinding
import com.example.forgetnoting.habit.adapter.HabitListAdapter
import com.example.forgetnoting.habit.viewmodel.HabitViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabitListFragment : Fragment(R.layout.fragment_habit_list) {

    private lateinit var binding: FragmentHabitListBinding
    private val viewModel: HabitViewModel by viewModels()
    private lateinit var habitListAdapter: HabitListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitListAdapter = HabitListAdapter()
        binding.rvHabits.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = habitListAdapter
            val itemDivider = DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
            ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
                itemDivider.setDrawable(it)
            }
            addItemDecoration(itemDivider)

        }
        binding.fabAddHabit.setOnClickListener {
            findNavController().navigate(R.id.action_habitListFragment_to_addHabitFragment)
        }

        viewModel.habitsLiveData.observe(viewLifecycleOwner) {
            habitListAdapter.setData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHabitList()
    }
}