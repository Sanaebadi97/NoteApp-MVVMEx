package test.test.noteapp.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import kotlinx.android.synthetic.main.activity_add_note.*
import test.test.noteapp.R

class AddNoteActivity : AppCompatActivity() {


    lateinit var edtTitle: AppCompatEditText
    lateinit var edtDesc: AppCompatEditText
    lateinit var numberPicker: NumberPicker


    companion object {
        val EXTRA_TITLE: String = "test.test.noteapp.EXTRA_TITLE"
        val EXTRA_DESC: String = "test.test.noteapp.EXTRA_TITLE"
        val EXTRA_PRIORITY: String = "test.test.noteapp.EXTRA_PRIORITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)


        edtTitle = edt_title
        edtDesc = edt_desc
        numberPicker = np_priority
        numberPicker.minValue = 1
        numberPicker.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = getString(R.string.add_note)

    }

    private fun saveNote() {
        val title: String = edtTitle.text.toString()
        val desc: String = edtDesc.text.toString()
        val priority = numberPicker.value

        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(
                this@AddNoteActivity,
                getString(R.string.warn_insert),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESC, desc)
        data.putExtra(EXTRA_PRIORITY, priority)
        setResult(Activity.RESULT_OK, data)
        finish()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.save_note -> saveNote()

            else -> return super.onOptionsItemSelected(item)

        }

        return true

    }
}
