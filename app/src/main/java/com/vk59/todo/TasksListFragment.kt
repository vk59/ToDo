package com.vk59.todo

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.vk59.todo.databinding.FragmentTasksListBinding

class TasksListFragment : Fragment() {
    private lateinit var tasksListViewModel: TasksListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Init data binding
        val binding: FragmentTasksListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_tasks_list, container, false
        )
        binding.lifecycleOwner = this

        // Setting taskListViewModel to binding
        tasksListViewModel = ViewModelProvider(this).get(TasksListViewModel::class.java)
        binding.tasksListViewModel = tasksListViewModel

        setHasOptionsMenu(true)
        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(it).navigate(
                R.id.action_taskListFragment_to_addTaskFragment
            );
        }

        tasksListViewModel.tasksList.observe(viewLifecycleOwner, { tasks ->
            if (tasks != null) {
                val stringBuilder = StringBuilder()
                for (task in tasks) {
                    stringBuilder.append(
                        task.taskName + " " + task.taskImportance + "\n"
                    )
                }
                binding.tasksList.text = stringBuilder.toString()
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all) {
            // This View ask ViewModel to delete all tasks
            tasksListViewModel.deleteAllTasks()
            return true
        } else {
            return NavigationUI.onNavDestinationSelected(item, NavHostFragment.findNavController(
                this)) || super.onOptionsItemSelected(item)
        }
    }
}