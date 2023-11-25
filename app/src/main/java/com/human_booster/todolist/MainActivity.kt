package com.human_booster.todolist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.human_booster.todolist.ui.theme.ToDoListTheme
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException

class MainActivity : ComponentActivity() {

    private var items: ArrayList<String> = ArrayList()
    private lateinit var itemsAdapter: ArrayAdapter<String>
    private lateinit var lvItems: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvItems= findViewById(R.id.lvItems)
        readItems()
        itemsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lvItems.adapter = itemsAdapter
//        items.add("First Item")
//        items.add("Second Item")

        setupListViewListener()
    }

    fun onAddItem(v: View) {
        val etNewItem: EditText = findViewById(R.id.etNewItem)
        val itemText: String = etNewItem.text.toString()
        itemsAdapter.add(itemText)
        etNewItem.setText("")
        writeItems()
    }

    private fun setupListViewListener() {
        lvItems.setOnItemLongClickListener { adapter, item, pos, id ->

            // Remove the item within array at position
            items.removeAt(pos)

            // Refresh the adapter
            itemsAdapter.notifyDataSetChanged()

            // write to text file
            writeItems()

            // Return true consumes the long click event (marks it handled)
            true
        }
    }

    private fun readItems() {
        val filesDir: File = filesDir
        val todoFile = File(filesDir, "todo.txt")
        Log.d("MainActivity", "$todoFile")
        try {
            items = ArrayList((FileUtils.readLines(todoFile) as? List<String>) ?: emptyList())
        } catch (e: IOException) {
            items = ArrayList()
        }
    }

    private fun writeItems() {
        val filesDir: File = filesDir
        val todoFile = File(filesDir, "todo.txt")
        try {
            FileUtils.writeLines(todoFile, items)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}
