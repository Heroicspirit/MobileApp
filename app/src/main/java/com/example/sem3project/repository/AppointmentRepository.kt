package com.example.sem3project.repository

import com.example.sem3project.model.Appointment

interface AppointmentRepository {
    fun saveAppointment(appointment: Appointment, callback: (Boolean) -> Unit)
    fun updateAppointment(appointmentId: String, appointmentText: String, callback: (Boolean) -> Unit)
    fun deleteAppointment(appointmentId: String, callback: (Boolean) -> Unit)
    fun loadAppointments(callback: (List<Appointment>) -> Unit)
}
