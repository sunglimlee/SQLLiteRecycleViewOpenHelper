package com.example.sqlliterecycleviewopenhelper

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//두개의 클래스 모두 생성자 함수 필요
class GroceryAdapter(private val mContext : Context, private var mCursor : Cursor) : RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {
    //************ 아주 중요한부분이다. mExampleList 처럼 mCursor 을 넘겨받아와서 binding 을 해준다.. ***************
    //Context 와 Cursor 가 필요하다. 외부에서 들어오는 것들이므로 생성자에서 만들어주자.. 그리고 내부에서 사용하니깐 내부 변수 만들고..
    //여기서 itemView 가 넘어가야 하는데... 자동으로 만들어주는구나...
    class GroceryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //여기서 Private 을 하면 밑에 함수에서 안보이는데.. 자꾸 private 를 한다.
        var nameText : TextView = itemView.findViewById(R.id.textview_name_item)
        var countText : TextView = itemView.findViewById(R.id.textview_amount_item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        //또 헷갈렸다. 지금은 그냥 뷰를 만드는부분에서 작업하고 있다... ㅋ ^^
        val inflater : LayoutInflater = LayoutInflater.from(mContext)
        val view : View = inflater.inflate(R.layout.grocery_item, parent, false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {

        //이안에 실제로 값을 집어넣는다고 했지? 데이터를 집어넣어야 하는데 데이터가 어디에 있니? 데이터 베이스에서 가지고 와야하나
        //왜냐하면 이건 지금 데이터베이스로 작업을 하고 있으니깐?????
        //mCursor 을 이용해서 작업을 해준다. 말되네..
        if (!mCursor.moveToPosition(position)) //이말은 아무것도 없다는 뜻이네..
            return
        val name : String = mCursor.getString(mCursor.getColumnIndexOrThrow(GroceryContract.COLUMN_NAME))
        val amount : Int = mCursor.getInt(mCursor.getColumnIndexOrThrow(GroceryContract.COLUMN_AMOUNT))
        val id : Long = mCursor.getLong(mCursor.getColumnIndexOrThrow(GroceryContract.COLUMN_PID))
        holder.nameText.text = name
        holder.countText.text = amount.toString()
        holder.itemView.tag = id
    }

    override fun getItemCount(): Int {
        return mCursor.count
    }
    fun swapCursor(newCursor : Cursor) {
        if (mCursor != null) {
            mCursor.close()
        }
        mCursor = newCursor
        if (mCursor != null) {
            notifyDataSetChanged()
        }

    }
}