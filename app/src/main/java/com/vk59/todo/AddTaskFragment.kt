package com.vk59.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AddTaskFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                    AddTaskFragment().apply {
                        arguments = Bundle().apply {
                            putString(ARG_PARAM1, param1)
                            putString(ARG_PARAM2, param2)
                }
            }
    }
}