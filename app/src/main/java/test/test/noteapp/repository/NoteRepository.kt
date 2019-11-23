package test.test.noteapp.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import test.test.noteapp.database.Note
import test.test.noteapp.database.NoteDao
import test.test.noteapp.database.NoteDatabase

class NoteRepository constructor(application: Application) : AndroidViewModel(application) {
    private var noteDao: NoteDao? = null
    private var allNotes: LiveData<List<Note>>? = null

    init {
        val database = NoteDatabase.getInstance(application)
        noteDao = database.noteDao
        allNotes = noteDao!!.getAllNotes()

    }

    fun insert(note: Note) {
        InsertAsync(noteDao!!).execute(note)
    }

    fun update(note: Note) {
        UpdateAsync(noteDao!!).execute(note)
    }

    fun delete(note: Note) {
        DeleteAsync(noteDao!!).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllAsync(noteDao!!).execute()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes!!
    }


    companion object {

        class InsertAsync(private val noteDao: NoteDao) : AsyncTask<Note, Boolean, Boolean>() {

            override fun doInBackground(vararg params: Note?): Boolean {
                noteDao.insertNote(params[0]!!)
                return true
            }

        }


        class UpdateAsync(private val noteDao: NoteDao) : AsyncTask<Note, Boolean, Boolean>() {

            override fun doInBackground(vararg params: Note?): Boolean {
                noteDao.update(params[0]!!)
                return true
            }

        }

        class DeleteAsync(private val noteDao: NoteDao) : AsyncTask<Note, Boolean, Boolean>() {

            override fun doInBackground(vararg params: Note?): Boolean {
                noteDao.delete(params[0]!!)
                return true
            }

        }


        class DeleteAllAsync(private val noteDao: NoteDao) : AsyncTask<Void, Boolean, Boolean>() {

            override fun doInBackground(vararg params: Void?): Boolean {
                noteDao.deleteAllNotes()
                return true
            }


        }
    }
}