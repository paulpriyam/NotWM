package com.example.forgetnoting.habit.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.forgetnoting.R
import com.example.forgetnoting.databinding.FragmentAddHabitBinding
import com.example.forgetnoting.habit.model.Habit
import com.example.forgetnoting.habit.viewmodel.HabitViewModel
import com.example.forgetnoting.util.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddHabitFragment : Fragment(R.layout.fragment_add_habit), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentAddHabitBinding
    private val viewModel: HabitViewModel by viewModels()
    private lateinit var imagePicker: ActivityResultLauncher<Intent>

    private val imageFlow = MutableStateFlow("")
    private val habitTitleFlow = MutableStateFlow("")
    private val habitDescFlow = MutableStateFlow("")
    private val tagFlow = MutableStateFlow("")
    private val targetDaysFlow = MutableStateFlow(30)
    private val completedDaysFlow = MutableStateFlow(0)

    private val isValid = combine(
        imageFlow,
        habitTitleFlow,
        habitDescFlow,
        tagFlow,
        targetDaysFlow,
        completedDaysFlow
    ) { array ->
        val validImage = (array[0] as String).isNotBlank()
        val validTitle = (array[1] as String).isNotBlank()
        val validDesc = (array[2] as String).isNotBlank()
        val validTag = (array[3] as String).isNotBlank()
        val target = (array[4] as Int)
        val completed = (array[5] as Int)
        val validTarget = target >= completed
        validImage && validTitle && validDesc && validTag && validTarget
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagePicker =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                    val selectedImageUri = intent?.data
                    if (selectedImageUri != null) {
                        imageFlow.value = selectedImageUri.toString()
                        binding.ivHabit.setImageURI(selectedImageUri)
                    }
                }
            }
        binding.ivHabit.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePicker.launch(intent)
        }

        binding.etGoal.doAfterTextChanged { text ->
            if (text.isNullOrBlank()) {
                targetDaysFlow.value = 0
            } else {
                targetDaysFlow.value = text.toString().toInt()
            }
        }
        binding.etDaysCompleted.doAfterTextChanged { text ->
            if (text.isNullOrBlank()) {
                completedDaysFlow.value = 0
            } else {
                completedDaysFlow.value = text.toString().toInt()
            }
        }

        binding.etHabitTitle.doAfterTextChanged { text ->
            habitTitleFlow.value = text.toString()
        }

        binding.etHabitDesc.doAfterTextChanged { text ->
            habitDescFlow.value = text.toString()
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.habit_tags,
            android.R.layout.simple_spinner_dropdown_item
        ).also {
            binding.spinner.adapter = it
        }
        binding.spinner.onItemSelectedListener = this

        binding.btnAddHabit.setOnClickListener {
            binding.ivHabit.invalidate()
            val bitmap = binding.ivHabit.drawable.toBitmap()
            val habit = Habit(
                title = habitTitleFlow.value.toString(),
                tag = tagFlow.value,
                createdAt = System.currentTimeMillis(),
                goal = targetDaysFlow.value,
                daysCompleted = completedDaysFlow.value,
                image = bitmap
            )
            Log.d("Habit --->", "$habit")
            viewModel.insertHabit(habit)
        }

        viewModel.insertHabitLiveDate.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is ViewState.Error -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_SHORT).show()
                }
                is ViewState.Success -> {
                    binding.progress.visibility = View.GONE
                    findNavController().navigate(R.id.action_addHabitFragment_to_habitListFragment)
                }
                is ViewState.Cancel -> {
                    binding.progress.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launch {
            isValid.collect {
                binding.btnAddHabit.isEnabled = it
            }
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val tag = parent?.getItemAtPosition(position).toString()
        tagFlow.value = tag
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // No implementation required
    }
}