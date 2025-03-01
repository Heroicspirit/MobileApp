package com.example.sem3project.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sem3project.model.Appointment
import com.example.sem3project.repository.FirebaseAppointmentRepository

class AppointmentViewModel(private val repository: FirebaseAppointmentRepository) : ViewModel() {

    private val _appointments = MutableLiveData<List<Appointment>>()
    val appointments: LiveData<List<Appointment>> get() = _appointments

    fun loadAppointments() {
        repository.loadAppointments { appointments ->
            _appointments.value = appointments
        }
    }

    fun saveAppointment(appointment: Appointment) {
        repository.saveAppointment(appointment) { success ->
            if (success) loadAppointments()  // Refresh the list after saving
        }
    }

    fun updateAppointment(appointmentId: String, appointmentText: String) {
        repository.updateAppointment(appointmentId, appointmentText) { success ->
            if (success) loadAppointments()  // Refresh after updating
        }
    }

    fun deleteAppointment(appointmentId: String) {
        repository.deleteAppointment(appointmentId) { success ->
            if (success) loadAppointments()  // Refresh after deleting
        }
    }
}
