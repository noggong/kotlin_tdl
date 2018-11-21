package com.dazziman.example;

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import com.dazziman.example.R

class TaskAdapter(tasks: MutableList<Task> = ArrayList()) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var isEditMode = false
    var tasks: MutableList<Task> = tasks
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val context = parent?.context
        val view = LayoutInflater.from(context)?.inflate(R.layout.list_item_task, parent, false)
        val holder = TaskViewHolder(view!!)

        if(view != null)
        {
            view.setOnLongClickListener {
                val task = tasks.get(holder.adapterPosition)
                task.completed = !task.completed

                if(task.completed) {
                    view.setBackgroundColor(Color.GREEN)
                }
                else
                    view.setBackgroundColor(Color.WHITE)

                isEditMode = true

                // 편의를 위해 넣는게 좋은지 잘 모르겠다. Long Touch 시 체크박스 체크해주는 기능
//                task.checked = task.completed
//                notifyDataSetChanged()

                true
            }
            view.setOnClickListener(){
                val task = tasks.get(holder.adapterPosition)
                val intent = Intent(context, AddTaskActivity::class.java)
                intent.putExtra(MainActivity.DATA_TASK, task)
                intent.putExtra(MainActivity.EDIT_INDEX, holder.adapterPosition)
                (context as Activity).startActivityForResult(intent, MainActivity.ADD_TASK_REQUEST)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder?.bindTask(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun addTask(task: Task) {
        tasks.add(task)
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view?.findViewById(R.id.task_title) as TextView
        val selectCheckBox = view?.findViewById(R.id.task_completed) as CheckBox
        val rowView = view

        fun bindTask(task: Task) {
            titleTextView.text = task.title
            selectCheckBox.isChecked = task.checked
            when(task.completed) {
                true -> rowView.setBackgroundColor(Color.GREEN)
                false -> rowView.setBackgroundColor(Color.WHITE)
            }

            selectCheckBox.setOnCheckedChangeListener { _, isChecked ->
                tasks[adapterPosition].checked = isChecked
            }
        }
    }
}