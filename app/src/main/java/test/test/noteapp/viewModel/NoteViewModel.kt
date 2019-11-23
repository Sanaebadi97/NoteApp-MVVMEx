package test.test.noteapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import test.test.noteapp.database.Note
import test.test.noteapp.repository.NoteRepository

class NoteViewModel constructor(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository = NoteRepository(application)
    private var allNotes: LiveData<List<Note>>? = null

    init {
        allNotes = noteRepository.getAllNotes()
    }

    fun insert(note: Note) {
        noteRepository.insert(note)
    }

    fun update(note: Note) {
        noteRepository.update(note)
    }

    fun delete(note: Note) {
        noteRepository.delete(note)
    }

    fun deleteAllNotes() {
        noteRepository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>>? {
        return allNotes!!
    }


}