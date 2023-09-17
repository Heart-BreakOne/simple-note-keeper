package com.app.notesapplication

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class AddNotes : AppCompatActivity() {
    val dbTable = "Notes"
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)


        val edtTitle:EditText = findViewById(R.id.edtNoteTitle)
        val edtContent:EditText = findViewById(R.id.edtNoteContent)

            try {
                var bundle: Bundle? = intent.extras
                id = bundle!!.getInt("ID")
                if(id != 0) {
                    edtTitle.setText(bundle.getString("TITLE"))
                    edtContent.setText(bundle.getString("DESCRIPTION"))
                }
            } catch(ex:Exception) {}

    }

    fun buAdd(view: View) {
        val edtTitle:EditText = findViewById(R.id.edtNoteTitle)
        val edtContent:EditText = findViewById(R.id.edtNoteContent)
        //val title = .toString()
        //val content = e.toString()

        val dbManager = DbManager(this)
        var values = ContentValues()
        values.put("TITLE", edtTitle.text.toString())
        values.put("DESCRIPTION", edtContent.text.toString())

        if(id == 0) {
            val id = dbManager.Insert(values)
            if (id > 0) {
                Toast.makeText(this, "The note has been added", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            var selectionArgs = arrayOf(id.toString())
            val id = dbManager.update(values, "ID = ?", selectionArgs)
            if (id > 0) {
                Toast.makeText(this, "The note has been added", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "The note has not been added", Toast.LENGTH_SHORT).show()
            }

        }

    }
}