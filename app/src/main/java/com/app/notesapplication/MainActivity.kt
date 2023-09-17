package com.app.notesapplication

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*

class MainActivity : AppCompatActivity() {
    val listOfNotes = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadQuery("%")
        /*ADDING DUMMY DATA
        with(listOfNotes) {
            add(Notes(1, "FINISH COURSE", "LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM "))

        }*/


        loadQuery("%")
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show()
    }

    //FIRES WHENEVER RETURNING TO THE ACTIVITY
    override fun onResume() {
        super.onResume()
        loadQuery("%")
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }
    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show()
    }


    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show()
    }



    fun loadQuery(title:String){

        var dbManager = DbManager(this)
        val projections = arrayOf("ID", "TITLE", "DESCRIPTION")
        var selectionArgs = arrayOf(title)
        val cursor = dbManager.Query(projections, "TITLE LIKE ?", selectionArgs, "TITLE ASC")
        listOfNotes.clear()
        if(cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("TITLE"))
                val description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"))
                listOfNotes.add(Notes(id, title, description))
            } while(cursor.moveToNext())
        }

        val myNotesAdapter = myNotesAdapter(this, listOfNotes)
        val lsNotes = findViewById<ListView>(R.id.lsNotes)
        lsNotes.adapter = myNotesAdapter


    }

    //CHANGES MENU BAR BUTTONS
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)

        val searchView = menu!!.findItem(R.id.searchNote).actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean{
                Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
                loadQuery("%${query}%")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                //TODO (SEARCH DATABASE)
                return false
            }

        } )

        return super.onCreateOptionsMenu(menu)
    }

    //FIRES WHENEVER SOMEONE CLICKS ON A BUTTON FROM THE MENU
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // if(item != null) {     <-  THIS IS ALWAYS TRUE, THERE'S NO NEED TO USE IT. ]
        when (item.itemId) {
            R.id.addNoteMenu -> {
                //LOAD ADD NOTE ACTIVITY
                val intent = Intent(this, AddNotes::class.java)
                startActivity(intent)
                }
            }






        return super.onOptionsItemSelected(item)
    }


    inner class myNotesAdapter : BaseAdapter {

        var listNotesAdapter = ArrayList<Notes>()
        var context:Context? = null
        constructor(context:Context, listNotesAdapter: ArrayList<Notes>):super(){
            this.context = context
            this.listNotesAdapter = listNotesAdapter
        }

        override fun getCount(): Int {
            return listNotesAdapter.size
        }

        override fun getItem(position: Int): Any {
            return listNotesAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
            val myNote = listNotesAdapter[position]
            //val layoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val myView=layoutInflater.inflate(R.layout.note_ticket,null)

            val txtNoteTitle = myView.findViewById<TextView>(R.id.txtNoteTitle)
            val txtNoteContent = myView.findViewById<TextView>(R.id.txtNoteContent)
            txtNoteTitle.text = myNote.nodeName
            txtNoteContent.text=myNote.nodeDescription

            //DELETE A NOTE IMPLEMENTATION
            val imgDelete = myView.findViewById<ImageView>(R.id.imgDelete)
            imgDelete.setOnClickListener(View.OnClickListener {
                val dbmanager = DbManager(this.context!!)
                val selectionArgs = arrayOf(myNote.nodeId.toString())
                dbmanager.delete("ID=?", selectionArgs)
                loadQuery("%")
                Toast.makeText(context, "Note deleted successfully", Toast.LENGTH_SHORT).show()
            })

            val imgEdit = myView.findViewById<ImageView>(R.id.imgEdit)
            imgEdit.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, AddNotes::class.java)

                intent.putExtra("ID", myNote.nodeId)
                intent.putExtra("TITLE", myNote.nodeName)
                intent.putExtra("DESCRIPTION",  myNote.nodeDescription)

                startActivity(intent)
            })











            return myView
        }


    }



}