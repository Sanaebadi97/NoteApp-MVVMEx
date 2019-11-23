package test.test.noteapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "note_title")
    var title: String = "",

    @ColumnInfo(name = "note_description")
    val description: String = "",

    @ColumnInfo(name = "note_priority")
    val priority: Int = 0
)
