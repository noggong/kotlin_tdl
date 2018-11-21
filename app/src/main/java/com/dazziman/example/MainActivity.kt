package com.dazziman.example

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.support.design.widget.FloatingActionButton
import android.util.Log


class MainActivity : AppCompatActivity()
{
    val adapter = TaskAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as? Toolbar
        setSupportActionBar(toolbar)

        val recyclerView = findViewById(R.id.task_list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_TASK_REQUEST && resultCode == Activity.RESULT_OK) {
            val task = Task(data?.getStringExtra(TITLE_TEXT).orEmpty(), data?.getStringExtra(DESCRIPTION_TEXT).orEmpty())
            val isEdit = data?.getBooleanExtra(IS_EDIT, false)
            if(isEdit == true) {
                val listIndex = data?.getIntExtra(EDIT_INDEX, WRONG_INDEX)
                editTask(listIndex!!, task)
            } else {
                adapter.addTask(task)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val tasks = Storage.readData(this)

        if (tasks != null && (adapter.tasks.isEmpty())) {
            adapter.tasks = tasks
        }
    }

    override fun onPause() {
        super.onPause()

        Storage.writeData(this, adapter.tasks)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_settings -> return true
            R.id.action_add -> {
                val intent = Intent(this, AddTaskActivity::class.java)
                startActivityForResult(intent, ADD_TASK_REQUEST)
                return true
            }
            R.id.action_delete -> {
                AlertDialog.Builder(this)
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton("확인") { dialogInterface, i ->
                            removeTasks()
                        }
                        .setNegativeButton("취소") { dialogInterface, i -> }
                        .show()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun removeTasks()
    {
        val iter = adapter.tasks.iterator()
        while (iter.hasNext()) {
            val task = iter.next()

            if (task.checked) {
                iter.remove()
            }
        }
        adapter.notifyDataSetChanged()
    }

    fun editTask(index: Int, task: Task)
    {
        adapter.tasks.set(index, task)
        adapter.notifyDataSetChanged()
    }

    // Java static과 동일하게 사용 가능
    companion object {
        val ADD_TASK_REQUEST                        = 0
        val WRONG_INDEX                             = -1

        val TITLE_TEXT                              = "TITLE"                                       //액티비티 이동간 Task Tile Text
        val DESCRIPTION_TEXT                        = "DESCRIPTION"                                 //Task Description Text
        val DATA_TASK                               = "DATA_TASK"                                   //Task Instance
        val IS_EDIT                                 = "IS_EDIT"                                     //
        val EDIT_INDEX                              = "EDIT_INDEX"

    }
}

