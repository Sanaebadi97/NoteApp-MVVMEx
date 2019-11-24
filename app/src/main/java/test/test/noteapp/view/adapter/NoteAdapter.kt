package test.test.noteapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item_list.view.*
import test.test.noteapp.R
import test.test.noteapp.database.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {


    private var noteList: List<Note>? = null
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item_list, parent, false)
        return NoteHolder(view)
    }

    override fun getItemCount(): Int {
        if (noteList!!.isNotEmpty()) {
            noteList!!.size

        } else {
            Log.e("NOTE_ADAPTER", "LIST IS 0")
            return 0
        }

        return noteList!!.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = this.noteList!![position]
        holder.txtTitle.text = note.title
        holder.txtDesc.text = note.description
        holder.txtPriority.text = note.priority.toString()
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle: AppCompatTextView = itemView.txt_title
        val txtDesc: AppCompatTextView = itemView.txt_desc
        val txtPriority: AppCompatTextView = itemView.txt_view_priority

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION)
                    onItemClickListener!!.onItemClick(noteList!![position])
            }
        }


    }

    fun setNotes(notes: List<Note>) {
        noteList = notes
        notifyDataSetChanged()

    }

    fun getNoteAt(position: Int): Note {
        return this.noteList!![position]
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener

    }
}