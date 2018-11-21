package com.dazziman.example;

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.support.v4.content.ContextCompat.getSystemService
import android.R.attr.key
import android.support.v4.app.TaskStackBuilder
import android.support.v4.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.activity_main.*


class AddTaskActivity : AppCompatActivity() {

    lateinit var edTitle : EditText
    lateinit var edDescription : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        var isEdit: Boolean = false
        // 툴바에 뒤로가기 버튼 추가
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        // Adapter로 부터 전달받은 데이터를 가져온다.
        val intent = getIntent()
        val task = intent.getSerializableExtra(MainActivity.DATA_TASK) as? Task
        val listLndex = intent.getSerializableExtra(MainActivity.EDIT_INDEX) as? Int

        edTitle = findViewById(R.id.task_title) as EditText
        edDescription = findViewById(R.id.task_description) as EditText
        val submit = findViewById(R.id.submit) as Button

        // Activity로 부터 전달된 데이터가 있다면
        if(task != null)
        {
            edTitle.setText(task.title)
            edDescription.setText(task.description)
            isEdit = true
            submit.setText(resources.getString(R.string.edit))
        }

        submit.setOnClickListener {

            if (edTitle.text?.toString().isNullOrBlank()) {
                edTitle.error = resources.getString(R.string.title_error)
            } else {
                submit(isEdit, listLndex)
            }
        }
    }

    fun submit(isEdit: Boolean, index: Int?)
    {
        val data = Intent()
        data.putExtra(MainActivity.TITLE_TEXT, edTitle.text.toString())
        data.putExtra(MainActivity.DESCRIPTION_TEXT, edDescription.text.toString())
        data.putExtra(MainActivity.IS_EDIT, isEdit)
        data.putExtra(MainActivity.EDIT_INDEX, index)
        setResult(Activity.RESULT_OK, data)
        hideSoftKeyboard()

        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard()
    }

    fun hideSoftKeyboard()
    {
        Log.e("dazziman", "hide")
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(edTitle.getWindowToken(), 0)
    }

}
