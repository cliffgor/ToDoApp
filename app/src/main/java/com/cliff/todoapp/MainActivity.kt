package com.cliff.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab=findViewById<View>(R.id.fab) as FloatingActionButton

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
    }
}