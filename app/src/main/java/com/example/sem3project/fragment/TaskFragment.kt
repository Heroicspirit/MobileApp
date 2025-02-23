package com.example.sem3project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sem3project.databinding.FragmentTaskBinding
import com.example.sem3project.viewmodel.TaskViewModel

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe the LiveData from ViewModel
        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            binding.recyclerView.adapter = TaskAdapter(tasks)
        }

        binding.btnAddTask.setOnClickListener {
            val task = Task(0, "New Task", "Task description")
            taskViewModel.insert(task)
        }

        return binding.root
    }
}
