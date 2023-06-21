package com.example.forgetnoting.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forgetnoting.R
import com.example.forgetnoting.reminder.adapter.ReminderAdapter
import com.example.forgetnoting.reminder.adapter.ReminderListPagingDataAdapter
import com.example.forgetnoting.databinding.FragmentReminderListBinding
import com.example.forgetnoting.reminder.viewmodel.ReminderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.security.Permissions


@AndroidEntryPoint
class ReminderListFragment : Fragment(R.layout.fragment_reminder_list) {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var _binding: FragmentReminderListBinding? = null
    private lateinit var reminderAdapter: ReminderAdapter
    private lateinit var reminderListPagingDataAdapter: ReminderListPagingDataAdapter
    private val binding get() = _binding!!

    private val viewModel: ReminderViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_reminderListFragment_to_addReminderFragment)
        }
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it == false) {
                    Toast.makeText(
                        requireContext(),
                        "Please Grant Notification from the settings page",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    binding.fabAdd.isEnabled = true
                }
            }
        reminderListPagingDataAdapter = ReminderListPagingDataAdapter()
//        reminderAdapter = ReminderAdapter(requireContext())
        binding.rvReminders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reminderListPagingDataAdapter
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
        lifecycleScope.launch {
            viewModel.reminderFlow.collect {
                reminderListPagingDataAdapter.submitData(it)
            }
        }

//        viewModel.reminders.observe(viewLifecycleOwner, Observer {
//            reminderAdapter.setData(it)
//        })
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
//        viewModel.getAllReminders()
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.fabAdd.isEnabled = true
        } else {
            binding.fabAdd.isEnabled = false
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}