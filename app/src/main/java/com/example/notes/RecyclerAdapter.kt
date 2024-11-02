package com.example.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val notes: MutableList<Notes>) :
    RecyclerView.Adapter<RecyclerAdapter.NotesViewHolder>() {

    private var onNotesClickListener: OnNotesClickListener? = null

    interface OnNotesClickListener {
        fun inItemClick(note: Notes)
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var idTV: TextView = itemView.findViewById(R.id.idTV)
        var dateTV: TextView = itemView.findViewById(R.id.dateTV)
        var noteTV: TextView = itemView.findViewById(R.id.noteTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun getItemCount() = notes.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]
        holder.idTV.text = "№ ${note.id.toString()}"
        holder.dateTV.text = note.date
        holder.noteTV.text = note.note
        holder.itemView.setOnClickListener {
            onNotesClickListener!!.inItemClick(note)
        }
    }

    fun setOnUserClickListener(onNotesClickListener: OnNotesClickListener) {
        this.onNotesClickListener = onNotesClickListener
    }
}