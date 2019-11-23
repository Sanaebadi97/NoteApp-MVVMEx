package test.test.noteapp.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        private var INSTANCE: NoteDatabase? = null
            private set

        fun getInstance(context: Context): NoteDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java, "note_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(CALLBACK)
                        .build()
                }
                return INSTANCE!!

            }
        }

        private val CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDbAsyncTask(INSTANCE!!).execute()
            }
        }


        private class populateDbAsyncTask(private var db: NoteDatabase) :
            AsyncTask<Void, Boolean, Boolean>() {
            val noteDao: NoteDao = db.noteDao

            override fun doInBackground(vararg params: Void?): Boolean {
                noteDao.insertNote(Note(0, "Test 1", "description 1", 1))
                noteDao.insertNote(Note(0, "Test 2", "description 2", 2))
                noteDao.insertNote(Note(0, "Test 3", "description 3", 3))
                return true
            }


        }

    }


}