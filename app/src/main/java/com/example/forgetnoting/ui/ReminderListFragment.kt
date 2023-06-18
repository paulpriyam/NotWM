package com.example.forgetnoting.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forgetnoting.R
import com.example.forgetnoting.adapter.ReminderAdapter
import com.example.forgetnoting.databinding.FragmentReminderListBinding
import com.example.forgetnoting.viewmodel.ReminderViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReminderListFragment : Fragment(R.layout.fragment_reminder_list) {

    private var _binding: FragmentReminderListBinding? = null
    private lateinit var reminderAdapter: ReminderAdapter
    private val binding get() = _binding!!

    private val viewModel: ReminderViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_reminderListFragment_to_addReminderFragment)
        }
        reminderAdapter = ReminderAdapter(requireContext())
        binding.rvReminders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reminderAdapter
            val itemDecoration = DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
            ContextCompat.getDrawable(requireContext(), R.drawable.divider)
                ?.let { itemDecoration.setDrawable(it) }
            addItemDecoration(
                itemDecoration
            )
        }

        viewModel.reminders.observe(viewLifecycleOwner, Observer {
            reminderAdapter.setData(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReminderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllReminders()
    }
}