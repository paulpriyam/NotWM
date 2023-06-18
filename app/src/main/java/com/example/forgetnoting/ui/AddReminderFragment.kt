package com.example.forgetnoting.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.forgetnoting.R
import com.example.forgetnoting.databinding.FragmentAddReminderBinding
import com.example.forgetnoting.model.Reminder
import com.example.forgetnoting.util.ReminderManager
import com.example.forgetnoting.viewmodel.ReminderViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AddReminderFragment : Fragment(R.layout.fragment_add_reminder) {

    private lateinit var binding: FragmentAddReminderBinding
    private val viewModel: ReminderViewModel by viewModels()
    private var defaultChipColor = R.color.yellow

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = CompoundButton.OnCheckedChangeListener { chip, isChecked ->
            if (isChecked) {
                val color = viewModel.fromChipIdToColor(chip.id)
                defaultChipColor = color
                binding.root.setBackgroundColor(resources.getColor(color))
            }
        }
        val chip =
            binding.chipGroup.findViewById<Chip>(viewModel.fromColorToChipId(defaultChipColor))
        chip?.let {
            binding.root.setBackgroundColor(resources.getColor(defaultChipColor))
            binding.chipScroll.smoothScrollTo(chip.left - chip.paddingLeft, chip.top)
        }
        binding.chipPink.setOnCheckedChangeListener(listener)
        binding.chipPurple.setOnCheckedChangeListener(listener)
        binding.chipPurpleLight.setOnCheckedChangeListener(listener)
        binding.chipPurpleDark.setOnCheckedChangeListener(listener)
        binding.chipRed.setOnCheckedChangeListener(listener)
        binding.chipTeal.setOnCheckedChangeListener(listener)
        binding.chipTealLight.setOnCheckedChangeListener(listener)
        binding.chipYellow.setOnCheckedChangeListener(listener)

        binding.etDate.setOnClickListener {
            openDatePickerDialog()
        }

        binding.etTime.setOnClickListener {
            openTimePickerDialog()
        }
        binding.btInsert.setOnClickListener {
            addReminder()
        }

        viewModel.rowId.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.IO) {
                val id = viewModel.getIdFromRowId(it).toInt()
                val reminder = viewModel.getReminderById(id)
                withContext(Dispatchers.Main) {
                    ReminderManager.startReminder(
                        requireContext(),
                        reminder.remindAt,
                        reminder.title,
                        reminder.description,
                        id
                    )
                    Log.d(
                        "Reminder created --->",
                        "reminderRowId =  $it  reminderId = $id ${reminder.toString()}"
                    )
                    findNavController().navigate(R.id.action_addReminderFragment_to_reminderListFragment)
                }
            }
        }
    }

    private fun openDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog( // on below line we are passing context.
            requireContext(),
            { view, selectedYear, selectedMonthOfYear, selectedDayOfMonth -> // on below line we are setting date to our edit text.
                binding.etDate.setText(selectedDayOfMonth.toString() + "-" + (selectedMonthOfYear + 1) + "-" + selectedYear)
            },  // on below line we are passing year,
            // month and day for selected date in our date picker.
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun openTimePickerDialog() {
        val today = Calendar.getInstance()
        val hour = today.get(Calendar.HOUR_OF_DAY)
        val minute = today.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(requireContext(), { view, selectedHour, selectedMinute ->
                binding.etTime.setText("$selectedHour:$selectedMinute")
            }, hour, minute, true)

        timePickerDialog.show()
    }

    private fun addReminder() {
        val group = viewModel.fromChipColorToGroup(defaultChipColor)
        val reminder = Reminder(
            id = null,
            remindAt = binding.etTime.text.toString(),
            createdAt = System.currentTimeMillis(),
            title = binding.etReminderTitle.text.toString(),
            description = binding.etReminderDesc.text.toString(),
            color = defaultChipColor,
            group = group
        )
        Log.d("Reminder --->", "${reminder.toString()}")
        viewModel.addReminder(reminder)

    }
}