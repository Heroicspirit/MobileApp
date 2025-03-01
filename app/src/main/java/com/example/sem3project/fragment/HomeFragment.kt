package com.example.sem3project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sem3project.databinding.FragmentHomeBinding
import com.example.sem3project.model.Appointment
import com.example.sem3project.ui.adapter.AppointmentAdapter
import com.example.sem3project.repository.FirebaseAppointmentRepository
import com.example.sem3project.viewmodel.AppointmentViewModel
import com.example.sem3project.viewmodel.AppointmentViewModelFactory

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var appointmentAdapter: AppointmentAdapter
    private val appointmentList = mutableListOf<Appointment>()

    // Use ViewModelProvider to initialize the ViewModel with a custom factory
    private val viewModel: AppointmentViewModel by viewModels {
        AppointmentViewModelFactory(FirebaseAppointmentRepository())  // Make sure the repository is properly initialized
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        // Initialize RecyclerView for Appointments
        appointmentAdapter = AppointmentAdapter(appointmentList)
        binding.appointmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.appointmentRecyclerView.adapter = appointmentAdapter

        // Observe the LiveData for appointment list
        viewModel.appointments.observe(viewLifecycleOwner) { appointments ->
            appointmentList.clear()
            appointmentList.addAll(appointments)
            appointmentAdapter.notifyDataSetChanged()
        }

        // Load existing appointments
        viewModel.loadAppointments()

        // Handle Save Appointment
        binding.btnSaveAppointment.setOnClickListener {
            val appointmentText = binding.editAppointment.text.toString().trim()
            if (appointmentText.isNotEmpty()) {
                val appointment = Appointment(title = "Sample Title", date = "2025-03-01", text = appointmentText)
                viewModel.saveAppointment(appointment)
            } else {
                Toast.makeText(requireContext(), "Please enter an appointment", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Update Appointment
        binding.btnUpdateAppointment.setOnClickListener {
            val appointmentId = binding.editAppointmentId.text.toString().trim()
            val appointmentText = binding.editAppointment.text.toString().trim()
            if (appointmentId.isNotEmpty() && appointmentText.isNotEmpty()) {
                viewModel.updateAppointment(appointmentId, appointmentText)
            } else {
                Toast.makeText(requireContext(), "Please provide valid appointment ID and text", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Delete Appointment
        binding.btnDeleteAppointment.setOnClickListener {
            val appointmentIdToDelete = binding.editAppointmentIdToDelete.text.toString().trim()
            if (appointmentIdToDelete.isNotEmpty()) {
                viewModel.deleteAppointment(appointmentIdToDelete)
            } else {
                Toast.makeText(requireContext(), "Please enter a valid appointment ID", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
