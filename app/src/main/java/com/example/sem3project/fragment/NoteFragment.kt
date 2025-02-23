package com.example.sem3project.fragment

package com.example.sem3project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sem3project.databinding.FragmentNoteBinding
import com.example.sem3project.viewmodel.NoteViewModel

class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe the LiveData from ViewModel
        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            binding.recyclerView.adapter = NoteAdapter(notes)
        }

        binding.btnAddNote.setOnClickListener {
            val note = Note(0, "New Note")
            noteViewModel.insert(note)
        }

        return binding.root
    }
}
