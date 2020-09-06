package com.vk59.todo

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.vk59.todo.databinding.FragmentEditTaskBinding

class EditTaskFragment : Fragment() {
    private lateinit var binding: FragmentEditTaskBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_task, container, false
        )
        setHasOptionsMenu(true)
        binding.lifecycleOwner = this
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        binding.taskViewModel = taskViewModel

        taskViewModel.task?.observe(viewLifecycleOwner, { task ->
            if (task != null) {
                binding.importanceSpinner.setSelection(task.taskImportance)
            }
        })

        taskViewModel.eventTaskUpdate.observe(viewLifecycleOwner, {
            if (it) {
                taskViewModel.eventTaskUpdateFinished()
                NavHostFragment.findNavController(this).navigate(
                    R.id.action_editTaskFragment_to_taskListFragment
                )
            }
        })
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.save_task) {
            taskViewModel.task!!.value?.taskName = binding.editTaskName.text.toString()
            taskViewModel.task!!.value?.taskDescription = binding.editTaskDescription.text.toString()
            taskViewModel.task!!.value?.taskImportance = binding.importanceSpinner.selectedItemPosition
            taskViewModel.updateTask()
            true
        } else {
            NavigationUI.onNavDestinationSelected(
                item,
                NavHostFragment.findNavController(this)
            ) || super.onOptionsItemSelected(item)
        }
    }

}