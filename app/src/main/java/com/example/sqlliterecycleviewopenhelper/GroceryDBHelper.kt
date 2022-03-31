package com.example.sqlliterecycleviewopenhelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.sqlliterecycleviewopenhelper.GroceryContract.GroceryEntry

//데이터베이스 테이블 지우는것도 연습하도록 하자..

class GroceryDBHelper(val context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME : String = "grocerylist1.db"
        const val DATABASE_VERSION : Int = 1
        const val SQL_CREATE_GROCERYLIST_TABLE = "CREATE TABLE " +
                GroceryEntry.TABLE_NAME + " (" +
                GroceryEntry.COLUMN_PID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GroceryEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                GroceryEntry.COLUMN_AMOUNT + " INTEGER NOT NULL, " +
                GroceryEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");"



    }
    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(SQL_CREATE_GROCERYLIST_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS " + GroceryEntry.TABLE_NAME)
        onCreate(p0)
    }
}