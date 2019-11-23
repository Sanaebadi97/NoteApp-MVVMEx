package test.test.noteapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun insertNote(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY note_priority DESC")
    fun getAllNotes(): LiveData<List<Note>>


}