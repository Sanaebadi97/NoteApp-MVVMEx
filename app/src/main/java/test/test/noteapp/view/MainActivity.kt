package test.test.noteapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import test.test.noteapp.R
import test.test.noteapp.view.adapter.NoteAdapter
import test.test.noteapp.viewModel.NoteViewModel

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    lateinit var noteAdapter: NoteAdapter
    lateinit var rvNote: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvNote = rv_note

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes()!!.observe(this, Observer { notes ->

            //update RecyclerView
            noteAdapter.setNotes(notes)
        })


        rvConfig()
    }

    private fun rvConfig() {
        noteAdapter = NoteAdapter()
        rvNote.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvNote.layoutManager = linearLayoutManager
        rvNote.adapter = noteAdapter

    }
}
