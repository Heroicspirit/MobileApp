package com.example.sem3project.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sem3project.repository.NoteRepository
import com.example.sem3project.repository.NoteRepositoryImpl

class NoteViewModel : ViewModel() {

    private val noteRepository: NoteRepository = NoteRepositoryImpl()

    // Function to save or update note
    fun saveOrUpdateNote(noteId: String, userId: String, note: String, callback: (Boolean, String) -> Unit) {
        noteRepository.addOrUpdateNote(noteId, userId, note, callback)
    }

    // Function to delete note
    fun deleteNote(noteId: String, callback: (Boolean, String) -> Unit) {
        noteRepository.deleteNote(noteId, callback)
    }

    // Function to fetch notes (can be added as needed)
    fun fetchNotes(userId: String, callback: (Boolean, List<Pair<String, String>>?, String?) -> Unit) {
        noteRepository.fetchNotes(userId, callback)
    }
}
