package test.test.noteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        private var instance: NoteDatabase? = null
    }

    fun getInstance(context: Context): NoteDatabase {
        synchronized(this) {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java, "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!

        }
    }
}