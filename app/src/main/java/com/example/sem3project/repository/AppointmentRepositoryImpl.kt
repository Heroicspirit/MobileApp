package com.example.sem3project.repository

import com.example.sem3project.model.Appointment
import com.google.firebase.database.FirebaseDatabase

class FirebaseAppointmentRepository : AppointmentRepository {

    private val database = FirebaseDatabase.getInstance().reference.child("appointments")

    override fun saveAppointment(appointment: Appointment, callback: (Boolean) -> Unit) {
        val appointmentId = database.push().key!!
        val newAppointment = appointment.copy(id = appointmentId)

        database.child(appointmentId).setValue(newAppointment).addOnCompleteListener { task ->
            callback(task.isSuccessful)
        }
    }

    override fun updateAppointment(appointmentId: String, appointmentText: String, callback: (Boolean) -> Unit) {
        val appointmentReference = database.child(appointmentId)
        appointmentReference.child("text").setValue(appointmentText).addOnCompleteListener { task ->
            callback(task.isSuccessful)
        }
    }

    override fun deleteAppointment(appointmentId: String, callback: (Boolean) -> Unit) {
        val appointmentReference = database.child(appointmentId)
        appointmentReference.removeValue().addOnCompleteListener { task ->
            callback(task.isSuccessful)
        }
    }

    override fun loadAppointments(callback: (List<Appointment>) -> Unit) {
        database.get().addOnSuccessListener { snapshot ->
            val appointmentList = snapshot.children.mapNotNull { it.getValue(Appointment::class.java) }
            callback(appointmentList)
        }
    }
}
