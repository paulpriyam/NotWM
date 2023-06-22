package com.example.forgetnoting.habit.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forgetnoting.R
import com.example.forgetnoting.databinding.FragmentHabitListBinding
import com.example.forgetnoting.habit.adapter.HabitListAdapter
import com.example.forgetnoting.habit.viewmodel.HabitViewModel
import com.example.forgetnoting.util.toDp
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt

@AndroidEntryPoint
class HabitListFragment : Fragment(R.layout.fragment_habit_list) {

    private lateinit var dragHelper: ItemTouchHelper
    private lateinit var swipeHelper: ItemTouchHelper

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

        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val height = toDp(requireContext(), displayMetrics.heightPixels / displayMetrics.density)
        val width = toDp(requireContext(), displayMetrics.widthPixels / displayMetrics.density)

        val deleteIcon = resources.getDrawable(R.drawable.ic_delete, null)
        val favouriteIcon = resources.getDrawable(R.drawable.ic_favourite, null)

        val deleteColor = resources.getColor(android.R.color.holo_red_light)
        val favouriteColor = resources.getColor(android.R.color.holo_green_light)

        swipeHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            val deleteBackground = ColorDrawable(Color.RED)
            val swipeInitiatedBackground = ColorDrawable(Color.GRAY)
            val favouriteBackground = ColorDrawable(Color.GREEN)
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.absoluteAdapterPosition
                if (direction == ItemTouchHelper.LEFT) {

                } else {

                }
            }

            override fun onChildDrawOver(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                // 1. Background color based on swipe direction.
                when {
                    abs(dX) < width / 3 -> {
                        if (dX > 0) {
                            swipeInitiatedBackground.setBounds(
                                viewHolder.itemView.left,
                                viewHolder.itemView.top,
                                viewHolder.itemView.left + dX.toInt(),
                                viewHolder.itemView.bottom
                            )
                            swipeInitiatedBackground.draw(canvas)
                        } else {
                            swipeInitiatedBackground.setBounds(
                                viewHolder.itemView.right + dX.toInt(),
                                viewHolder.itemView.top,
                                viewHolder.itemView.right,
                                viewHolder.itemView.bottom
                            )
                            swipeInitiatedBackground.draw(canvas)
                        }
                    }
                    dX > width / 3 -> {
                        deleteBackground.setBounds(
                            viewHolder.itemView.left,
                            viewHolder.itemView.top,
                            viewHolder.itemView.left + dX.toInt(),
                            viewHolder.itemView.bottom
                        )
                        deleteBackground.draw(canvas)
                    }
                    else -> {
                        favouriteBackground.setBounds(
                            viewHolder.itemView.right + dX.toInt(),
                            viewHolder.itemView.top,
                            viewHolder.itemView.right,
                            viewHolder.itemView.bottom
                        )
                        favouriteBackground.draw(canvas)
                    }
                }

                // 2. Printing the Icons
                val textMargin = resources.getDimension(R.dimen.text_margin)
                    .roundToInt()

                deleteIcon.bounds = Rect(
                    textMargin,
                    viewHolder.itemView.top + textMargin + toDp(requireContext(), 8f),
                    textMargin + deleteIcon.intrinsicWidth,
                    viewHolder.itemView.top + deleteIcon.intrinsicHeight
                            + textMargin + toDp(requireContext(), 8f)
                )

                favouriteIcon.bounds = Rect(
                    width - 3 * textMargin - favouriteIcon.intrinsicWidth,
                    viewHolder.itemView.top + textMargin + toDp(requireContext(), 8f),
                    width - 3 * textMargin,
                    viewHolder.itemView.top + favouriteIcon.intrinsicHeight
                            + textMargin + toDp(requireContext(), 8f)
                )

                //3. Drawing icon based upon direction swiped
                if (dX > 0) deleteIcon.draw(canvas) else favouriteIcon.draw(canvas)

                super.onChildDrawOver(
                    canvas,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        })
        swipeHelper.attachToRecyclerView(binding.rvHabits)

//        dragHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
//        ) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                viewHolder.itemView.elevation = 16F
//
//                val from = viewHolder.adapterPosition
//                val to = target.adapterPosition
//
//                Collections.swap(list, from, to)
//                habitListAdapter.notifyItemMoved(from, to)
//                return true
//            }
//
//            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
//                super.onSelectedChanged(viewHolder, actionState)
//                viewHolder?.itemView?.elevation = 0F
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit
//
//        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHabitList()
    }
}