package com.app.notesapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast


class DbManager {
    val dbName = "MYNOTES"
    val dbTable = "NOTES"
    val colId = "ID"
    val colTitle = "TITLE"
    val colDesc = "DESCRIPTION"
    val dbVersion = 1
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS ${dbTable} (${colId} INTEGER PRIMARY KEY, ${colTitle} TEXT, ${colDesc} TEXT);"
    var sqlDb:SQLiteDatabase? = null



    constructor(context:Context){
        var db = DatabaseHelperNotes(context)
        sqlDb = db.writableDatabase
    }

    inner class DatabaseHelperNotes:SQLiteOpenHelper{

        var context:Context? = null
        constructor(context:Context):super(context, dbName, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context, "Database has been created", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

            p0!!.execSQL("DROP TABLE IF EXISTS ${dbTable}")

        }

    }

    fun Insert(values:ContentValues): Long {
        return sqlDb!!.insert(dbTable, null, values)
    }

    // PROJECTION -> WHICH COLUMN YOU WANT TO SELECT
    //SELECTION -> WHICH ROWS YOU WANT TO SELECT
    fun Query(projection:Array<String>, selection:String, selectionArgs:Array<String>, sorOrder:String): Cursor {
        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        return qb.query(sqlDb, projection, selection, selectionArgs, null, null, sorOrder)
    }

    fun delete(selection:String, selectionArgs: Array<String>):Int {
        //val count         return count
        return sqlDb!!.delete(dbTable, selection, selectionArgs)
    }


    fun update(values: ContentValues, selection:String, selectionArgs: Array<String>) :Int {
        return sqlDb!!.update(dbTable, values, selection, selectionArgs)

    }

}