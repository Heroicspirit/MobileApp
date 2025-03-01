package com.example.sem3project.repository

import com.example.sem3project.model.NoteModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class NoteRepositoryImpl : NoteRepository {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val noteRef: DatabaseReference = database.reference.child("notes")

    override fun addOrUpdateNote(noteId: String, userId: String, note: String, callback: (Boolean, String) -> Unit) {
        val noteData = NoteModel(noteId, userId, note)
        noteRef.child(noteId).setValue(noteData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, "Note saved successfully")
            } else {
                callback(false, task.exception?.message ?: "Failed to save note")
            }
        }
    }

    override fun deleteNote(noteId: String, callback: (Boolean, String) -> Unit) {
        noteRef.child(noteId).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, "Note deleted successfully")
            } else {
                callback(false, task.exception?.message ?: "Failed to delete note")
            }
        }
    }

    override fun fetchNotes(userId: String, callback: (Boolean, List<Pair<String, String>>?, String?) -> Unit) {
        noteRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notesList = mutableListOf<Pair<String, String>>()
                for (noteSnapshot in snapshot.children) {
                    val noteId = noteSnapshot.key ?: continue
                    val noteText = noteSnapshot.child("note").value.toString()
                    notesList.add(Pair(noteId, noteText))
                }
                callback(true, notesList, null)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, null, error.message)
            }
        })
    }
}
