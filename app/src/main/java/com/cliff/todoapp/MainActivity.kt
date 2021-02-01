package com.cliff.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    var toDoList:MutableList<ToDoModel>? = null
    lateinit var adapter: ToDoAdapter

    private var listViewItem :ListView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab=findViewById<View>(R.id.fab) as FloatingActionButton
        listViewItem= findViewById<ListView>(R.id.item_listView)

        database = FirebaseDatabase.getInstance().reference
        fab.setOnClickListener { View ->
            val alertDialog = AlertDialog.Builder(this)
            val textEditText = EditText(this)
            alertDialog.setMessage("Add your todo Item")
            alertDialog.setTitle("Enter Todo Item")
            alertDialog.setView(textEditText)
            alertDialog.setPositiveButton("Add") {dialogue, i ->
                val todoItemData = ToDoModel.createList()
                todoItemData.ItemDataText =textEditText.text.toString()
                todoItemData.done = false


                val newItemData = database.child("todo").push()
                todoItemData.UID = newItemData.key


                newItemData.setValue(todoItemData)

                dialogue.dismiss()
                Toast.makeText(this, " Todo item added Successfully", Toast.LENGTH_LONG).show()

            }
            alertDialog.show()

        }

        toDoList= mutableListOf<ToDoModel>()
        adapter= ToDoAdapter(this, toDoList!!)
        listViewItem!!.adapter=adapter
        database.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                toDoList!!.clear()
                addItemToList(p0)

            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "You have not added anything to your todo", Toast.LENGTH_LONG).show()

            }

        })

    }

    private fun addItemToList(p0: DataSnapshot) {

        val items=p0.children.iterator()

        if(items.hasNext()) {
            val toDoIndexValue=items.next()
            val itemsIterator = toDoIndexValue.children.iterator()

            while (itemsIterator.hasNext()) {
                val currentItem=itemsIterator.next()
                val toDoItemData = ToDoModel.createList()
                val map = currentItem.getValue() as HashMap<String, Any>


                toDoItemData.UID=currentItem.key
                toDoItemData.done=map.get("done") as Boolean?
                toDoItemData.ItemDataText=map.get("itemTextData")as String?
                toDoList!!.add(toDoItemData)
            }
        }

        adapter.notifyDataSetChanged()

    }
}