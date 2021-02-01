package com.cliff.todoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ToDoAdapter(context: Context, toDoList:MutableList<ToDoModel>) : BaseAdapter {

    private val inflater:LayoutInflater = LayoutInflater.from(context)
    private var itemList = toDoList
    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val UID : String = itemList.get(p0).UID as String
        val itemTextData = itemList.get(p0).ItemDataText as String
        val done: Boolean = itemList.get(p0).done as Boolean

        val  view : View
        val viewHolder : ListViewHolder

        if (p1==null) {
            view=inflater.inflate()
        }

    }

   private class ListViewHolder(row:View?) {
       val textLabel:TextView=row!!.findViewById(R.id.item_textView) as TextView
       val isDone: CheckBox=row!!.findViewById(R.id.checkbox) as CheckBox
       val isDeleted: ImageButton = row!!.findViewById(R.id.close) as ImageButton


    }
}