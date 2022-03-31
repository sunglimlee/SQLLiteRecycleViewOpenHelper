package com.example.sqlliterecycleviewopenhelper

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var mDatabase: SQLiteDatabase
    private lateinit var mAdapter : GroceryAdapter
    private lateinit var mEditTextName : EditText
    private lateinit var mTextViewAmount : TextView
    private lateinit var mButtonIncrease: Button
    private lateinit var mButtonDecrease : Button
    private lateinit var mButtonAdd : Button
    private lateinit var recyclerView: RecyclerView
    private var mAmount : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = GroceryDBHelper(this) //피곤하나??? ^^
        mDatabase = dbHelper.writableDatabase
        //removeTable()
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = GroceryAdapter(this, getAllItems())
        recyclerView.adapter = mAdapter

        // Swipe 작업하는 부분
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeItem(viewHolder.itemView.tag)
            }
        }).attachToRecyclerView(recyclerView)

        mEditTextName = findViewById(R.id.edittext_name)
        mTextViewAmount = findViewById(R.id.textview_amount)
        mButtonDecrease = findViewById(R.id.button_decrease)
        mButtonIncrease = findViewById(R.id.button_increase)
        mButtonAdd = findViewById(R.id.button_add)

        mButtonIncrease.setOnClickListener {
            increase()
        }
        mButtonDecrease.setOnClickListener {
            decrease()
        }
        mButtonAdd.setOnClickListener {
            addItem()
        }
    }

    private fun removeTable() {
        mDatabase.execSQL("DROP TABLE IF EXISTS " + GroceryContract.TABLE_NAME)
    }

    private fun removeItem(tag: Any?) {
        mDatabase.delete(GroceryContract.TABLE_NAME,
            GroceryContract.GroceryEntry.COLUMN_PID + " = " + tag, null)
        mAdapter.swapCursor(getAllItems())
    }

    private fun increase() {
        mAmount++
        mTextViewAmount.text = mAmount.toString()
    }
    private fun decrease() {
        if (mAmount > 0) {
            mAmount--
            mTextViewAmount.text = mAmount.toString()
        }
    }
    private fun addItem() {
        if (mEditTextName.text.toString().trim().isEmpty() || mAmount == 0) {
            return
        }
        val name : String = mEditTextName.text.toString()
        val cv = ContentValues()
        cv.put(GroceryContract.COLUMN_NAME, name)
        cv.put(GroceryContract.COLUMN_AMOUNT, mAmount)
        //데이터베이스 새로 시작하는 부분이네.. 그래서 swapCursor 를 해주는거구나.
        mDatabase.insert(GroceryContract.TABLE_NAME, null, cv)
        mAdapter.swapCursor(getAllItems())
        mEditTextName.text.clear()
    }
    private fun getAllItems() : Cursor {
        return mDatabase.query(
            GroceryContract.TABLE_NAME, null, null, null, null, null, GroceryContract.COLUMN_TIMESTAMP + " DESC"
        )
    }
}