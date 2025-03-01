package com.example.sem3project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sem3project.databinding.FragmentNotesBinding
import com.example.sem3project.model.NoteModel
import com.example.sem3project.viewmodel.NoteViewModel
import com.example.sem3project.adapter.NotesAdapter
import com.google.firebase.auth.FirebaseAuth

class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private val noteViewModel: NoteViewModel by viewModels()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    private lateinit var notesAdapter: NotesAdapter
    private val notesList = mutableListOf<NoteModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)

        // Set up RecyclerView
        notesAdapter = NotesAdapter(notesList)
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewNotes.adapter = notesAdapter

        // Fetch notes from ViewModel
        fetchNotes()

        // Save Note
        binding.btnSaveNote.setOnClickListener {
            val noteText = binding.editNote.text.toString().trim()
            if (noteText.isNotEmpty()) {
                saveNote(noteText)
            } else {
                Toast.makeText(requireContext(), "Note cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        // Update Note
        binding.btnUpdateNote.setOnClickListener {
            val noteId = binding.editNoteId.text.toString().trim()
            val newText = binding.editNote.text.toString().trim()
            if (noteId.isNotEmpty() && newText.isNotEmpty()) {
                updateNote(noteId, newText)
            } else {
                Toast.makeText(requireContext(), "Enter valid note ID and text", Toast.LENGTH_SHORT).show()
            }
        }

        // Delete Note
        binding.btnDeleteNote.setOnClickListener {
            val noteId = binding.editNoteId.text.toString().trim()
            if (noteId.isNotEmpty()) {
                deleteNote(noteId)
            } else {
                Toast.makeText(requireContext(), "Enter Note ID to delete", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    // Fetch notes from ViewModel and update RecyclerView
    private fun fetchNotes() {
        noteViewModel.fetchNotes(userId) { success, notes, message ->
            if (success) {
                notesList.clear()
                notes?.let {
                    notesList.addAll(it.map { note -> NoteModel(note.first, note.second) })
                }
                notesAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), message ?: "Failed to load notes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Save or update note
    private fun saveNote(note: String) {
        val noteId = System.currentTimeMillis().toString()
        noteViewModel.saveOrUpdateNote(noteId, userId, note) { success, message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    // Update note
    private fun updateNote(noteId: String, newText: String) {
        noteViewModel.saveOrUpdateNote(noteId, userId, newText) { success, message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    // Delete note
    private fun deleteNote(noteId: String) {
        noteViewModel.deleteNote(noteId) { success, message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}
