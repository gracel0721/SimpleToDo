package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {
    var taskList = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                taskList.removeAt(position)
                adapter.notifyDataSetChanged()
                saveItems()
            }
        }

        loadItems()
        val taskView = findViewById<RecyclerView>(R.id.taskView)
        adapter = TaskItemAdapter(taskList, onLongClickListener)
        val inputTextField= findViewById<EditText>(R.id.addTaskTxt)
        taskView.adapter = adapter
        taskView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.button).setOnClickListener {
            val userInputTask = inputTextField.text.toString()

            taskList.add(userInputTask)
            adapter.notifyItemInserted(taskList.size-1)
            inputTextField.setText("")
            saveItems()

        }
    }

    fun getDataFile(): File {
        return File(filesDir, "data.txt")
    }
    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), taskList)
        }
        catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
    fun loadItems(){
        try{
            taskList = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }
        catch(ioException: IOException){
            ioException.printStackTrace()
        }

    }
}