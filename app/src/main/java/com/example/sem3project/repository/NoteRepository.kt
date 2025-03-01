package com.example.sem3project.repository


interface NoteRepository {
    fun addOrUpdateNote(noteId: String, userId: String, note: String, callback: (Boolean, String) -> Unit)
    fun deleteNote(noteId: String, callback: (Boolean, String) -> Unit)
    fun fetchNotes(userId: String, callback: (Boolean, List<Pair<String, String>>?, String?) -> Unit)
}