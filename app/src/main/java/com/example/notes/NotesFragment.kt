package com.example.notes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class NotesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = DBHelper(requireContext(), null)
        val notes: MutableList<Notes> = mutableListOf()
        val noteET = view.findViewById<EditText>(R.id.noteET)
        val updateBTN = view.findViewById<Button>(R.id.updateBTN)
        val notesListRV = view.findViewById<RecyclerView>(R.id.notesListRV)

        notesListRV.layoutManager = LinearLayoutManager(requireContext())
        val adapter = RecyclerAdapter(notes)
        notesListRV.adapter = adapter
        notesListRV.setHasFixedSize(true)
        notes.addAll((db.getInfo()))

        updateBTN.setOnClickListener {
            if (noteET.text.isEmpty()) {
                Toast.makeText(requireContext(), "Введите заметку", Toast.LENGTH_LONG).show()
            } else {
                val id = IdGenerator(db).addId()
                val note = noteET.text.toString()
                val date = SimpleDateFormat("dd:MM:yyyy HH:mm", Locale.getDefault())
                val currentDate = date.format(Date()).toString()
                val newNote = Notes(id, note, currentDate)
                db.addNote(newNote)
                notes.clear()
                notes.addAll(db.getInfo())
                adapter.notifyDataSetChanged()
            }
        }
    }

}