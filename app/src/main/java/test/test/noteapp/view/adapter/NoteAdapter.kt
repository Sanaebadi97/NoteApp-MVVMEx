package test.test.noteapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item_list.view.*
import test.test.noteapp.R
import test.test.noteapp.database.Note

class NoteAdapter :
    ListAdapter<Note, NoteAdapter.NoteHolder>(DIFF_CALLBACK) {


    companion object DIFF_CALLBACK : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.title.equals(newItem.title) &&
                    oldItem.description.equals(newItem.description) &&
                    oldItem.priority == newItem.priority
        }
    }

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item_list, parent, false)
        return NoteHolder(view)
    }


    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = getItem(position)
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
                    onItemClickListener!!.onItemClick(getItem(position))
            }
        }


    }


    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener

    }
}