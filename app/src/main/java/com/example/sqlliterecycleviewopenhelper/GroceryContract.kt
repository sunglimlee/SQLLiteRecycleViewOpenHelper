package com.example.sqlliterecycleviewopenhelper

import android.provider.BaseColumns

class GroceryContract()  {
    companion object GroceryEntry : BaseColumns {
        const val TABLE_NAME : String = "groceryList"
        const val COLUMN_PID : String = "pid"
        const val COLUMN_NAME : String = "name"
        const val COLUMN_AMOUNT : String = "amount"
        const val COLUMN_TIMESTAMP : String = "timeStamp"
    }
}