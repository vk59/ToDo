package com.vk59.todo

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.vk59.todo.database.Task
import com.vk59.todo.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var binding: FragmentAddTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_task, container, false
        )
        setHasOptionsMenu(true)
        binding.lifecycleOwner = this
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        taskViewModel.eventTaskUpdate.observe(viewLifecycleOwner, {
            if (it) {
                taskViewModel.eventTaskAddFinished()
                NavHostFragment.findNavController(this).navigate(
                    R.id.action_addTaskFragment_to_taskListFragment
                )
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.save_task) {
            taskViewModel.insertTask(
                Task(
                    binding.editTaskName.text.toString(),
                    binding.editTaskDescription.text.toString(),
                    Task.TASK_STATUS_NOT_FINISHED,
                    binding.importanceSpinner.selectedItemPosition
                )
            )
            true
        } else {
            NavigationUI.onNavDestinationSelected(
                item,
                NavHostFragment.findNavController(this)
            ) || super.onOptionsItemSelected(item)
        }
    }

}