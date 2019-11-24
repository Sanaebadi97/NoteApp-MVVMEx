package test.test.noteapp.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import test.test.noteapp.R
import test.test.noteapp.database.Note
import test.test.noteapp.view.adapter.NoteAdapter
import test.test.noteapp.viewModel.NoteViewModel

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    lateinit var noteAdapter: NoteAdapter
    lateinit var rvNote: RecyclerView

    private lateinit var fabAddNote: FloatingActionButton

    companion object {
        const val ADD_NOTE_REQUEST: Int = 1
        const val EDIT_NOTE_REQUEST: Int = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAddNote = fab_add_note
        rvNote = rv_note


        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes()!!.observe(this, Observer { notes ->

            //update RecyclerView
            noteAdapter.setNotes(notes)
        })



        rvConfig()


        fabAddNote.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)

        }

        /*delete items*/

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.adapterPosition))
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.note_deleted),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvNote)

    }

    private fun rvConfig() {
        noteAdapter = NoteAdapter()
        rvNote.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvNote.layoutManager = linearLayoutManager
        rvNote.adapter = noteAdapter

        noteAdapter.setOnItemClickListener(
            object : NoteAdapter.OnItemClickListener {
                override fun onItemClick(note: Note) {
                    val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
                    intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
                    intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.title)
                    intent.putExtra(AddEditNoteActivity.EXTRA_DESC, note.description)
                    intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.priority)
                    startActivityForResult(intent, EDIT_NOTE_REQUEST)


                }

            }
        )


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val title: String = data!!.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val desc: String = data!!.getStringExtra(AddEditNoteActivity.EXTRA_DESC)
            val priority: Int = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

            val note = Note(title = title, description = desc, priority = priority)
            noteViewModel.insert(note)

            Toast.makeText(this, getString(R.string.note_saved), Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {

            val id: Int = data!!.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, getString(R.string.note_not_updated), Toast.LENGTH_SHORT)
                    .show()
                return
            }

            val title: String = data!!.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val desc: String = data!!.getStringExtra(AddEditNoteActivity.EXTRA_DESC)
            val priority: Int = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

            val note = Note(title = title, description = desc, priority = priority)
            note.id = id
            noteViewModel.update(note)

            Toast.makeText(this, getString(R.string.note_updated), Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, getString(R.string.note_not_saved), Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, getString(R.string.all_notes_deleted), Toast.LENGTH_SHORT)
                    .show()

            }

            else -> return super.onOptionsItemSelected(item)

        }

        return true

    }
}
