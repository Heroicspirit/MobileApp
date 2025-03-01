package com.example.sem3project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sem3project.databinding.ItemNoteBinding
import com.example.sem3project.model.NoteModel

class NotesAdapter(private val notes: List<NoteModel>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    // ViewHolder class to hold the item view
    inner class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    // Inflate the layout for each item and bind it to the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    // Bind the data to the item view
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.textViewNoteId.text = note.noteId
        holder.binding.textViewNoteContent.text = note.content
    }

    // Return the number of notes in the list
    override fun getItemCount(): Int {
        return notes.size
    }
}
