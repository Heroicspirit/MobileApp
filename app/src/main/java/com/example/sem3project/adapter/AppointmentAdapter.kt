package com.example.sem3project.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sem3project.databinding.ItemAppointmentBinding
import com.example.sem3project.model.Appointment

class AppointmentAdapter(private val appointmentList: MutableList<Appointment>) :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val binding = ItemAppointmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AppointmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointmentList[position]
        holder.bind(appointment)
    }

    override fun getItemCount(): Int = appointmentList.size

    inner class AppointmentViewHolder(private val binding: ItemAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(appointment: Appointment) {
            binding.tvAppointmentTitle.text = appointment.title // Bind title
            binding.tvAppointmentDate.text = appointment.date // Bind date
            binding.tvAppointmentText.text = appointment.text // Bind description
        }
    }
}
